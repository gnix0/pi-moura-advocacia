package com.mouraadvocacia.api.domain.port.inbound;

import java.util.List;

import com.mouraadvocacia.api.domain.model.Customer;

public interface FindCustomerUseCase {

    List<Customer> findCustomerByNameOrEmail(String query);
}
