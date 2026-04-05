package com.mouraadvocacia.api.adapter.outbound.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.mouraadvocacia.api.adapter.outbound.persistence.mapper.CustomerPersistenceMapper;
import com.mouraadvocacia.api.adapter.outbound.persistence.repository.SpringDataCustomerRepository;
import com.mouraadvocacia.api.domain.model.Customer;
import com.mouraadvocacia.api.domain.port.outbound.CustomerRepositoryPort;

@Component
public class CustomerPersistenceAdapter implements CustomerRepositoryPort {

    private final SpringDataCustomerRepository repository;
    private final CustomerPersistenceMapper mapper;

    public CustomerPersistenceAdapter(
            SpringDataCustomerRepository repository,
            CustomerPersistenceMapper mapper
    ) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmailIgnoreCase(email);
    }

    @Override
    public Customer save(Customer customer) {
        return mapper.toDomain(repository.save(mapper.toEntity(customer)));
    }

    @Override
    public List<Customer> findAll() {
        return mapper.toDomainList(repository.findAll());
    }

    @Override
    public List<Customer> findByNameOrEmail(String query) {
        return mapper.toDomainList(
                repository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                        query,
                        query,
                        query
                )
        );
    }

    @Override
    public Optional<Customer> findById(UUID id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
