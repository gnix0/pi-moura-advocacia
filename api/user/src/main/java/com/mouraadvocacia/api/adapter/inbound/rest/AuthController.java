package com.mouraadvocacia.api.adapter.inbound.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mouraadvocacia.api.adapter.inbound.rest.mapper.UserRestMapper;
import com.mouraadvocacia.api.adapter.inbound.rest.request.LoginRequest;
import com.mouraadvocacia.api.adapter.inbound.rest.request.RegisterUserRequest;
import com.mouraadvocacia.api.adapter.inbound.rest.response.AuthResponse;
import com.mouraadvocacia.api.adapter.inbound.rest.response.AuthMessageResponse;
import com.mouraadvocacia.api.adapter.inbound.rest.response.UserProfileResponse;
import com.mouraadvocacia.api.application.dto.AuthenticationResult;
import com.mouraadvocacia.api.domain.model.User;
import com.mouraadvocacia.api.domain.port.inbound.AuthenticateUserUseCase;
import com.mouraadvocacia.api.domain.port.inbound.FindUserByEmailUseCase;
import com.mouraadvocacia.api.domain.port.inbound.RegisterUserUseCase;
import com.mouraadvocacia.api.exception.AuthenticationFailureException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final RegisterUserUseCase registerUserUseCase;
    private final AuthenticateUserUseCase authenticateUserUseCase;
    private final FindUserByEmailUseCase findUserByEmailUseCase;
    private final UserRestMapper userRestMapper;

    public AuthController(
            RegisterUserUseCase registerUserUseCase,
            AuthenticateUserUseCase authenticateUserUseCase,
            FindUserByEmailUseCase findUserByEmailUseCase,
            UserRestMapper userRestMapper
    ) {
        this.registerUserUseCase = registerUserUseCase;
        this.authenticateUserUseCase = authenticateUserUseCase;
        this.findUserByEmailUseCase = findUserByEmailUseCase;
        this.userRestMapper = userRestMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthMessageResponse> register(@Valid @RequestBody RegisterUserRequest request) {
        registerUserUseCase.register(userRestMapper.toDomain(request));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AuthMessageResponse("Usuario cadastrado com sucesso."));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthenticationResult result = authenticateUserUseCase.authenticate(request.email(), request.password());

        return ResponseEntity.ok(userRestMapper.toAuthResponse(result));
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> me(Authentication authentication) {
        User user = findUserByEmailUseCase.findByEmail(authentication.getName())
                .orElseThrow(() -> new AuthenticationFailureException("Usuario autenticado nao encontrado."));

        return ResponseEntity.ok(userRestMapper.toProfileResponse(user));
    }
}
