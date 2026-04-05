package com.mouraadvocacia.api.adapter.inbound.rest.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.mouraadvocacia.api.adapter.inbound.rest.request.CreateCustomerRequest;
import com.mouraadvocacia.api.adapter.inbound.rest.request.UpdateCustomerRequest;
import com.mouraadvocacia.api.adapter.inbound.rest.response.CustomerResponse;
import com.mouraadvocacia.api.commmon.config.GlobalMapperConfig;
import com.mouraadvocacia.api.domain.model.Customer;

@Mapper(config = GlobalMapperConfig.class)
public interface CustomerRestMapper {

    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID())")
    Customer toDomain(CreateCustomerRequest request);

    @Mapping(target = "id", ignore = true)
    Customer toDomain(UpdateCustomerRequest request);

    CustomerResponse toResponse(Customer customer);

    List<CustomerResponse> toResponseList(List<Customer> customers);
}
