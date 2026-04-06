package com.mouraadvocacia.api.adapter.outbound.persistence;

import org.springframework.stereotype.Component;

import com.mouraadvocacia.api.adapter.outbound.persistence.entity.UserEntity;
import com.mouraadvocacia.api.domain.model.User;

@Component
public class UserPersistenceMapper {

    public UserEntity toEntity(User user) {
        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setEmail(user.getEmail());
        entity.setFirstName(user.getFirstName());
        entity.setLastName(user.getLastName());
        entity.setPassword(user.getPassword());
        return entity;
    }

    public User toDomain(UserEntity entity) {
        return new User(
                entity.getId(),
                entity.getEmail(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getPassword()
        );
    }
}
