package com.mouraadvocacia.api.adapter.outbound.persistence;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.mouraadvocacia.api.adapter.outbound.persistence.repository.SpringDataUserRepository;
import com.mouraadvocacia.api.domain.model.User;
import com.mouraadvocacia.api.domain.port.outbound.UserRepositoryPort;

@Component
public class UserPersistenceAdapter implements UserRepositoryPort {

    private final SpringDataUserRepository repository;
    private final UserPersistenceMapper mapper;

    public UserPersistenceAdapter(
            SpringDataUserRepository repository,
            UserPersistenceMapper mapper
    ) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmailIgnoreCase(email);
    }

    @Override
    public User save(User user) {
        return mapper.toDomain(repository.save(mapper.toEntity(user)));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmailIgnoreCase(email).map(mapper::toDomain);
    }
}
