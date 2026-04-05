package com.mouraadvocacia.api.adapter.inbound.rest.mapper;

import org.springframework.stereotype.Component;

import com.mouraadvocacia.api.adapter.inbound.rest.request.RegisterUserRequest;
import com.mouraadvocacia.api.adapter.inbound.rest.response.AuthResponse;
import com.mouraadvocacia.api.adapter.inbound.rest.response.UserProfileResponse;
import com.mouraadvocacia.api.application.dto.AuthenticationResult;
import com.mouraadvocacia.api.domain.model.User;

@Component
public class UserRestMapper {

    public User toDomain(RegisterUserRequest request) {
        return User.createNew(
                request.email(),
                request.firstName(),
                request.lastName(),
                request.password()
        );
    }

    public AuthResponse toAuthResponse(AuthenticationResult result) {
        return new AuthResponse(
                result.token(),
                result.userId(),
                result.email(),
                result.firstName(),
                result.lastName()
        );
    }

    public UserProfileResponse toProfileResponse(User user) {
        return new UserProfileResponse(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName()
        );
    }
}
