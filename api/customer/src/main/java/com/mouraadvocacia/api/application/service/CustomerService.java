package com.mouraadvocacia.api.application.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mouraadvocacia.api.commmon.exception.GlobalException;
import com.mouraadvocacia.api.commmon.exception.InvalidParameterException;
import com.mouraadvocacia.api.commmon.exception.ResourceNotFoundException;
import com.mouraadvocacia.api.domain.model.Customer;
import com.mouraadvocacia.api.domain.port.inbound.CreateCustomerPort;
import com.mouraadvocacia.api.domain.port.inbound.DeleteCustomerByIdUseCase;
import com.mouraadvocacia.api.domain.port.inbound.FindCustomerUseCase;
import com.mouraadvocacia.api.domain.port.inbound.UpdateCustomerUseCase;
import com.mouraadvocacia.api.domain.port.outbound.CustomerRepositoryPort;

@Service
public class CustomerService implements
        CreateCustomerPort,
        FindCustomerUseCase,
        UpdateCustomerUseCase,
        DeleteCustomerByIdUseCase {

    private final CustomerRepositoryPort customerRepositoryPort;

    public CustomerService(CustomerRepositoryPort customerRepositoryPort) {
        this.customerRepositoryPort = customerRepositoryPort;
    }

    @Override
    @Transactional
    public void createCustomer(Customer customer) throws GlobalException, InvalidParameterException {
        validateCustomer(customer);

        if (customerRepositoryPort.existsByEmail(customer.getEmail().trim())) {
            throw new InvalidParameterException("Ja existe um cliente cadastrado com este e-mail.");
        }

        customer.setEmail(customer.getEmail().trim());
        customer.setFirstName(customer.getFirstName().trim());
        customer.setLastName(customer.getLastName().trim());
        customer.setPhoneNumber(customer.getPhoneNumber().trim());

        customerRepositoryPort.save(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Customer> findCustomerByNameOrEmail(String query) {
        if (query == null || query.isBlank()) {
            return customerRepositoryPort.findAll();
        }

        return customerRepositoryPort.findByNameOrEmail(query.trim());
    }

    @Override
    @Transactional
    public void updateCustomer(UUID id, Customer customer) throws ResourceNotFoundException {
        Customer existingCustomer = customerRepositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente nao encontrado."));

        mergeCustomer(existingCustomer, customer);
        customerRepositoryPort.save(existingCustomer);
    }

    @Override
    @Transactional
    public void deleteCustomerById(UUID id) throws ResourceNotFoundException {
        if (customerRepositoryPort.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("Cliente nao encontrado.");
        }

        customerRepositoryPort.deleteById(id);
    }

    private void validateCustomer(Customer customer) throws InvalidParameterException {
        if (customer == null) {
            throw new InvalidParameterException("Os dados do cliente sao obrigatorios.");
        }

        if (isBlank(customer.getFirstName())) {
            throw new InvalidParameterException("O primeiro nome do cliente e obrigatorio.");
        }

        if (isBlank(customer.getLastName())) {
            throw new InvalidParameterException("O sobrenome do cliente e obrigatorio.");
        }

        if (isBlank(customer.getEmail())) {
            throw new InvalidParameterException("O e-mail do cliente e obrigatorio.");
        }

        if (isBlank(customer.getPhoneNumber())) {
            throw new InvalidParameterException("O telefone do cliente e obrigatorio.");
        }
    }

    private void mergeCustomer(Customer existingCustomer, Customer customer) {
        if (!isBlank(customer.getFirstName())) {
            existingCustomer.setFirstName(customer.getFirstName().trim());
        }

        if (!isBlank(customer.getLastName())) {
            existingCustomer.setLastName(customer.getLastName().trim());
        }

        if (!isBlank(customer.getEmail())) {
            existingCustomer.setEmail(customer.getEmail().trim());
        }

        if (!isBlank(customer.getPhoneNumber())) {
            existingCustomer.setPhoneNumber(customer.getPhoneNumber().trim());
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
