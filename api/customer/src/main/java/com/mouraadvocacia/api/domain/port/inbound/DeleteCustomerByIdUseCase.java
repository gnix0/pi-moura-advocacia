package com.mouraadvocacia.api.domain.port.inbound;

import java.util.UUID;

import com.mouraadvocacia.api.commmon.exception.ResourceNotFoundException;

public interface DeleteCustomerByIdUseCase {
    void deleteCustomerById(UUID id) throws ResourceNotFoundException;
}
