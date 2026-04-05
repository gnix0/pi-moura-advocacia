package com.mouraadvocacia.api.domain.port.outbound;

import java.util.Optional;

import com.mouraadvocacia.api.domain.model.User;

public interface UserRepositoryPort {

    boolean existsByEmail(String email);

    User save(User user);

    Optional<User> findByEmail(String email);
}
