package com.mouraadvocacia.api.application.dto;

import java.util.UUID;

public record AuthenticationResult(
        String token,
        UUID userId,
        String email,
        String firstName,
        String lastName
) {
}
