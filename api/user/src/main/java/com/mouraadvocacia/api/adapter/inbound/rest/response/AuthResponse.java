package com.mouraadvocacia.api.adapter.inbound.rest.response;

import java.util.UUID;

public record AuthResponse(
        String token,
        UUID userId,
        String email,
        String firstName,
        String lastName
) {
}
