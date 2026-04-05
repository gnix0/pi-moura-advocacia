package com.mouraadvocacia.api.adapter.inbound.rest.request;

import jakarta.validation.constraints.Email;

public record UpdateCustomerRequest(
        @Email(message = "O e-mail informado e invalido.")
        String email,
        String firstName,
        String lastName,
        String phoneNumber
) {
}
