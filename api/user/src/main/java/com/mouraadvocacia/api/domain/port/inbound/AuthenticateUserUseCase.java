package com.mouraadvocacia.api.domain.port.inbound;

import com.mouraadvocacia.api.application.dto.AuthenticationResult;

public interface AuthenticateUserUseCase {

    AuthenticationResult authenticate(String email, String password);
}
