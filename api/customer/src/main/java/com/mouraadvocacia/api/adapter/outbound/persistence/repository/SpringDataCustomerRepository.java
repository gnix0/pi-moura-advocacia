package com.mouraadvocacia.api.adapter.outbound.persistence.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mouraadvocacia.api.adapter.outbound.persistence.entity.CustomerEntity;

public interface SpringDataCustomerRepository extends JpaRepository<CustomerEntity, UUID> {

    boolean existsByEmailIgnoreCase(String email);

    List<CustomerEntity> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
            String firstName,
            String lastName,
            String email
    );
}
