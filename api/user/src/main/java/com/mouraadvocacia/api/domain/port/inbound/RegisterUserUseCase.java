package com.mouraadvocacia.api.domain.port.inbound;

import com.mouraadvocacia.api.domain.model.User;

public interface RegisterUserUseCase {

    void register(User user);
}
