package com.mouraadvocacia.api.customer;

import java.util.UUID;

public class Customer {

    private final UUID id;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    public Customer(UUID id, String email, String firstName, String lastName, String phoneNumber) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public static Customer createNew(String email, String firstName, String lastName, String phoneNumber) {
        return new Customer(UUID.randomUUID(), email, firstName, lastName, phoneNumber);
    }
}
