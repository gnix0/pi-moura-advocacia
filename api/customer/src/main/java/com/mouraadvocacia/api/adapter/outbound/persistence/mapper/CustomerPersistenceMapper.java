package com.mouraadvocacia.api.adapter.outbound.persistence.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.mouraadvocacia.api.adapter.outbound.persistence.entity.CustomerEntity;
import com.mouraadvocacia.api.commmon.config.GlobalMapperConfig;
import com.mouraadvocacia.api.domain.model.Customer;

@Mapper(config = GlobalMapperConfig.class)
public interface CustomerPersistenceMapper {

    CustomerEntity toEntity(Customer customer);

    Customer toDomain(CustomerEntity entity);

    List<Customer> toDomainList(List<CustomerEntity> entities);
}
