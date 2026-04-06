package com.mouraadvocacia.api.adapter.inbound.rest.response;

import java.util.UUID;

public record CustomerResponse(
        UUID id,
        String email,
        String firstName,
        String lastName,
        String phoneNumber
) {
}
