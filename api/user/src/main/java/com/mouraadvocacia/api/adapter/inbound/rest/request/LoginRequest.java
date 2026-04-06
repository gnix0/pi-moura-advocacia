package com.mouraadvocacia.api.adapter.inbound.rest.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "O e-mail e obrigatorio.")
        @Email(message = "O e-mail informado e invalido.")
        String email,

        @NotBlank(message = "A senha e obrigatoria.")
        String password
) {
}
