package com.mouraadvocacia.api.adapter.inbound.rest.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateCustomerRequest(
        @NotBlank(message = "O e-mail do cliente e obrigatorio.")
        @Email(message = "O e-mail informado e invalido.")
        String email,

        @NotBlank(message = "O primeiro nome do cliente e obrigatorio.")
        String firstName,

        @NotBlank(message = "O sobrenome do cliente e obrigatorio.")
        String lastName,

        @NotBlank(message = "O telefone do cliente e obrigatorio.")
        String phoneNumber
) {
}
