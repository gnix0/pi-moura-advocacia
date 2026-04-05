package com.mouraadvocacia.api.domain.port.outbound;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.mouraadvocacia.api.domain.model.Customer;

public interface CustomerRepositoryPort {

    boolean existsByEmail(String email);

    Customer save(Customer customer);

    List<Customer> findAll();

    List<Customer> findByNameOrEmail(String query);

    Optional<Customer> findByEmail(String email);

    Optional<Customer> findById(UUID id);

    void deleteById(UUID id);
}
