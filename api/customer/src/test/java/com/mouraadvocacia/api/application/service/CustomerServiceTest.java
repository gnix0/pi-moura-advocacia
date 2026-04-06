package com.mouraadvocacia.api.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mouraadvocacia.api.commmon.exception.InvalidParameterException;
import com.mouraadvocacia.api.commmon.exception.ResourceConflictException;
import com.mouraadvocacia.api.commmon.exception.ResourceNotFoundException;
import com.mouraadvocacia.api.domain.model.Customer;
import com.mouraadvocacia.api.domain.port.outbound.CustomerRepositoryPort;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepositoryPort customerRepositoryPort;

    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        customerService = new CustomerService(customerRepositoryPort);
    }

    @Test
    void shouldCreateCustomerWhenDataIsValid() throws Exception {
        Customer customer = Customer.createNew("cliente@teste.com", "Ana", "Silva", "11999999999");

        when(customerRepositoryPort.existsByEmail(customer.getEmail())).thenReturn(false);

        customerService.createCustomer(customer);

        verify(customerRepositoryPort).save(customer);
    }

    @Test
    void shouldRejectDuplicateEmailWhenCreatingCustomer() {
        Customer customer = Customer.createNew("cliente@teste.com", "Ana", "Silva", "11999999999");

        when(customerRepositoryPort.existsByEmail(customer.getEmail())).thenReturn(true);

        ResourceConflictException exception = assertThrows(
                ResourceConflictException.class,
                () -> customerService.createCustomer(customer)
        );

        assertEquals("Ja existe um cliente cadastrado com este e-mail.", exception.getMessage());
        verify(customerRepositoryPort, never()).save(customer);
    }

    @Test
    void shouldReturnAllCustomersWhenQueryIsBlank() {
        List<Customer> customers = List.of(
                Customer.createNew("a@teste.com", "Ana", "Silva", "111111111"),
                Customer.createNew("b@teste.com", "Bruno", "Souza", "222222222")
        );

        when(customerRepositoryPort.findAll()).thenReturn(customers);

        List<Customer> result = customerService.findCustomerByNameOrEmail("   ");

        assertEquals(2, result.size());
    }

    @Test
    void shouldUpdateExistingCustomer() throws Exception {
        UUID customerId = UUID.randomUUID();
        Customer existingCustomer = new Customer(customerId, "old@teste.com", "Ana", "Silva", "111111111");
        Customer updateCustomer = new Customer(null, "new@teste.com", "Ana Paula", null, "999999999");

        when(customerRepositoryPort.findById(customerId)).thenReturn(Optional.of(existingCustomer));

        customerService.updateCustomer(customerId, updateCustomer);

        assertEquals("new@teste.com", existingCustomer.getEmail());
        assertEquals("Ana Paula", existingCustomer.getFirstName());
        assertEquals("Silva", existingCustomer.getLastName());
        assertEquals("999999999", existingCustomer.getPhoneNumber());
        verify(customerRepositoryPort).save(existingCustomer);
    }

    @Test
    void shouldRejectDuplicatedEmailWhenUpdatingCustomer() {
        UUID customerId = UUID.randomUUID();
        Customer existingCustomer = new Customer(customerId, "old@teste.com", "Ana", "Silva", "111111111");
        Customer otherCustomer = new Customer(UUID.randomUUID(), "used@teste.com", "Bruno", "Souza", "222222222");
        Customer updateCustomer = new Customer(null, "used@teste.com", null, null, null);

        when(customerRepositoryPort.findById(customerId)).thenReturn(Optional.of(existingCustomer));
        when(customerRepositoryPort.findByEmail("used@teste.com")).thenReturn(Optional.of(otherCustomer));

        ResourceConflictException exception = assertThrows(
                ResourceConflictException.class,
                () -> customerService.updateCustomer(customerId, updateCustomer)
        );

        assertEquals("Ja existe um cliente cadastrado com este e-mail.", exception.getMessage());
        verify(customerRepositoryPort, never()).save(existingCustomer);
    }

    @Test
    void shouldFailWhenDeletingUnknownCustomer() {
        UUID customerId = UUID.randomUUID();

        when(customerRepositoryPort.findById(customerId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> customerService.deleteCustomerById(customerId)
        );

        assertEquals("Cliente nao encontrado.", exception.getMessage());
    }

    @Test
    void shouldRejectCustomerWithoutRequiredFields() {
        Customer customer = Customer.createNew("", "", "", "");

        InvalidParameterException exception = assertThrows(
                InvalidParameterException.class,
                () -> customerService.createCustomer(customer)
        );

        assertTrue(exception.getMessage().contains("obrigatorio"));
    }
}
