package com.mouraadvocacia.api.adapter.inbound.rest.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterUserRequest(
        @NotBlank(message = "O e-mail e obrigatorio.")
        @Email(message = "O e-mail informado e invalido.")
        String email,

        @NotBlank(message = "O primeiro nome e obrigatorio.")
        String firstName,

        @NotBlank(message = "O sobrenome e obrigatorio.")
        String lastName,

        @NotBlank(message = "A senha e obrigatoria.")
        @Size(min = 8, message = "A senha deve ter pelo menos 8 caracteres.")
        String password
) {
}
