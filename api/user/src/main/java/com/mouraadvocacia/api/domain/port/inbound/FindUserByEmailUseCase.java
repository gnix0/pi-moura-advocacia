package com.mouraadvocacia.api.domain.port.inbound;

import java.util.Optional;

import com.mouraadvocacia.api.domain.model.User;

public interface FindUserByEmailUseCase {

    Optional<User> findByEmail(String email);
}
