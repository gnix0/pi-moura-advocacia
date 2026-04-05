package com.mouraadvocacia.api.adapter.inbound.rest;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mouraadvocacia.api.adapter.inbound.rest.mapper.CustomerRestMapper;
import com.mouraadvocacia.api.adapter.inbound.rest.request.CreateCustomerRequest;
import com.mouraadvocacia.api.adapter.inbound.rest.request.UpdateCustomerRequest;
import com.mouraadvocacia.api.adapter.inbound.rest.response.CustomerResponse;
import com.mouraadvocacia.api.adapter.inbound.rest.response.MessageResponse;
import com.mouraadvocacia.api.commmon.exception.GlobalException;
import com.mouraadvocacia.api.commmon.exception.InvalidParameterException;
import com.mouraadvocacia.api.commmon.exception.ResourceNotFoundException;
import com.mouraadvocacia.api.domain.port.inbound.CreateCustomerPort;
import com.mouraadvocacia.api.domain.port.inbound.DeleteCustomerByIdUseCase;
import com.mouraadvocacia.api.domain.port.inbound.FindCustomerUseCase;
import com.mouraadvocacia.api.domain.port.inbound.UpdateCustomerUseCase;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CreateCustomerPort createCustomerPort;
    private final FindCustomerUseCase findCustomerUseCase;
    private final UpdateCustomerUseCase updateCustomerUseCase;
    private final DeleteCustomerByIdUseCase deleteCustomerByIdUseCase;
    private final CustomerRestMapper customerRestMapper;

    public CustomerController(
            CreateCustomerPort createCustomerPort,
            FindCustomerUseCase findCustomerUseCase,
            UpdateCustomerUseCase updateCustomerUseCase,
            DeleteCustomerByIdUseCase deleteCustomerByIdUseCase,
            CustomerRestMapper customerRestMapper
    ) {
        this.createCustomerPort = createCustomerPort;
        this.findCustomerUseCase = findCustomerUseCase;
        this.updateCustomerUseCase = updateCustomerUseCase;
        this.deleteCustomerByIdUseCase = deleteCustomerByIdUseCase;
        this.customerRestMapper = customerRestMapper;
    }

    @PostMapping
    public ResponseEntity<MessageResponse> createCustomer(@Valid @RequestBody CreateCustomerRequest request)
            throws GlobalException, InvalidParameterException {
        createCustomerPort.createCustomer(customerRestMapper.toDomain(request));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new MessageResponse("Cliente cadastrado com sucesso."));
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> findCustomers(@RequestParam(required = false) String query) {
        return ResponseEntity.ok(
                customerRestMapper.toResponseList(findCustomerUseCase.findCustomerByNameOrEmail(query))
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> updateCustomer(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateCustomerRequest request
    ) throws ResourceNotFoundException {
        updateCustomerUseCase.updateCustomer(id, customerRestMapper.toDomain(request));

        return ResponseEntity.ok(new MessageResponse("Cliente atualizado com sucesso."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteCustomer(@PathVariable UUID id) throws ResourceNotFoundException {
        deleteCustomerByIdUseCase.deleteCustomerById(id);

        return ResponseEntity.ok(new MessageResponse("Cliente removido com sucesso."));
    }
}
