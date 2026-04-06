package com.mouraadvocacia.api.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mouraadvocacia.api.application.dto.AuthenticationResult;
import com.mouraadvocacia.api.domain.model.User;
import com.mouraadvocacia.api.domain.port.outbound.TokenProviderPort;
import com.mouraadvocacia.api.domain.port.outbound.UserRepositoryPort;
import com.mouraadvocacia.api.exception.AuthenticationFailureException;
import com.mouraadvocacia.api.exception.InvalidUserDataException;
import com.mouraadvocacia.api.exception.UserAlreadyExistsException;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepositoryPort userRepositoryPort;

    @Mock
    private TokenProviderPort tokenProviderPort;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepositoryPort, tokenProviderPort, passwordEncoder);
    }

    @Test
    void shouldRegisterUserWithEncodedPassword() {
        User user = User.createNew("advogado@teste.com", "Carlos", "Moura", "senha123");

        when(userRepositoryPort.existsByEmail("advogado@teste.com")).thenReturn(false);
        when(passwordEncoder.encode("senha123")).thenReturn("hash-seguro");

        userService.register(user);

        assertEquals("hash-seguro", user.getPassword());
        verify(userRepositoryPort).save(user);
    }

    @Test
    void shouldRejectDuplicateEmailOnRegistration() {
        User user = User.createNew("advogado@teste.com", "Carlos", "Moura", "senha123");

        when(userRepositoryPort.existsByEmail("advogado@teste.com")).thenReturn(true);

        UserAlreadyExistsException exception = assertThrows(
                UserAlreadyExistsException.class,
                () -> userService.register(user)
        );

        assertEquals("Ja existe um usuario cadastrado com este e-mail.", exception.getMessage());
        verify(userRepositoryPort, never()).save(any());
    }

    @Test
    void shouldAuthenticateAndReturnJwtData() {
        UUID userId = UUID.randomUUID();
        User user = new User(userId, "advogado@teste.com", "Carlos", "Moura", "hash-seguro");

        when(userRepositoryPort.findByEmail("advogado@teste.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("senha123", "hash-seguro")).thenReturn(true);
        when(tokenProviderPort.generateToken(userId, "advogado@teste.com")).thenReturn("jwt-token");

        AuthenticationResult result = userService.authenticate("advogado@teste.com", "senha123");

        assertEquals("jwt-token", result.token());
        assertEquals(userId, result.userId());
    }

    @Test
    void shouldRejectInvalidCredentials() {
        User user = new User(UUID.randomUUID(), "advogado@teste.com", "Carlos", "Moura", "hash-seguro");

        when(userRepositoryPort.findByEmail("advogado@teste.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("senha123", "hash-seguro")).thenReturn(false);

        AuthenticationFailureException exception = assertThrows(
                AuthenticationFailureException.class,
                () -> userService.authenticate("advogado@teste.com", "senha123")
        );

        assertEquals("E-mail ou senha invalidos.", exception.getMessage());
    }

    @Test
    void shouldRejectInvalidRegistrationDataWithBadRequestSemantics() {
        User user = User.createNew("advogado@teste.com", "Carlos", "Moura", "123");

        InvalidUserDataException exception = assertThrows(
                InvalidUserDataException.class,
                () -> userService.register(user)
        );

        assertEquals("A senha deve ter pelo menos 8 caracteres.", exception.getMessage());
        verify(userRepositoryPort, never()).save(any());
    }
}
