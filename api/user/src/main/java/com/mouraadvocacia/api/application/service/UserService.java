package com.mouraadvocacia.api.application.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mouraadvocacia.api.application.dto.AuthenticationResult;
import com.mouraadvocacia.api.domain.model.User;
import com.mouraadvocacia.api.domain.port.inbound.AuthenticateUserUseCase;
import com.mouraadvocacia.api.domain.port.inbound.FindUserByEmailUseCase;
import com.mouraadvocacia.api.domain.port.inbound.RegisterUserUseCase;
import com.mouraadvocacia.api.domain.port.outbound.TokenProviderPort;
import com.mouraadvocacia.api.domain.port.outbound.UserRepositoryPort;
import com.mouraadvocacia.api.exception.AuthenticationFailureException;
import com.mouraadvocacia.api.exception.UserAlreadyExistsException;

@Service
public class UserService implements
        RegisterUserUseCase,
        AuthenticateUserUseCase,
        FindUserByEmailUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final TokenProviderPort tokenProviderPort;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepositoryPort userRepositoryPort,
            TokenProviderPort tokenProviderPort,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepositoryPort = userRepositoryPort;
        this.tokenProviderPort = tokenProviderPort;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void register(User user) {
        validateForRegistration(user);

        String normalizedEmail = user.getEmail().trim().toLowerCase();

        if (userRepositoryPort.existsByEmail(normalizedEmail)) {
            throw new UserAlreadyExistsException("Ja existe um usuario cadastrado com este e-mail.");
        }

        user.setEmail(normalizedEmail);
        user.setFirstName(user.getFirstName().trim());
        user.setLastName(user.getLastName().trim());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepositoryPort.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public AuthenticationResult authenticate(String email, String password) {
        if (isBlank(email) || isBlank(password)) {
            throw new AuthenticationFailureException("E-mail e senha sao obrigatorios.");
        }

        User user = userRepositoryPort.findByEmail(email.trim().toLowerCase())
                .orElseThrow(() -> new AuthenticationFailureException("E-mail ou senha invalidos."));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new AuthenticationFailureException("E-mail ou senha invalidos.");
        }

        String token = tokenProviderPort.generateToken(user.getId(), user.getEmail());

        return new AuthenticationResult(
                token,
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        if (isBlank(email)) {
            return Optional.empty();
        }

        return userRepositoryPort.findByEmail(email.trim().toLowerCase());
    }

    private void validateForRegistration(User user) {
        if (user == null) {
            throw new AuthenticationFailureException("Os dados do usuario sao obrigatorios.");
        }

        if (isBlank(user.getFirstName())) {
            throw new AuthenticationFailureException("O primeiro nome e obrigatorio.");
        }

        if (isBlank(user.getLastName())) {
            throw new AuthenticationFailureException("O sobrenome e obrigatorio.");
        }

        if (isBlank(user.getEmail())) {
            throw new AuthenticationFailureException("O e-mail e obrigatorio.");
        }

        if (isBlank(user.getPassword())) {
            throw new AuthenticationFailureException("A senha e obrigatoria.");
        }

        if (user.getPassword().trim().length() < 8) {
            throw new AuthenticationFailureException("A senha deve ter pelo menos 8 caracteres.");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
