package com.mouraadvocacia.api.domain.port.inbound;

import java.util.UUID;

import com.mouraadvocacia.api.commmon.exception.ResourceNotFoundException;
import com.mouraadvocacia.api.domain.model.Customer;

public interface UpdateCustomerUseCase {
    void updateCustomer(UUID id, Customer customer) throws ResourceNotFoundException;
}
