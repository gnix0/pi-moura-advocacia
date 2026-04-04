package com.mouraadvocacia.api.model.user;

import java.util.UUID;

public class User {

    private final UUID id;
    private String password;
    private String email;
    private String firstName;
    private String lastName;

    public User(UUID id, String email, String firstName, String lastName) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UUID getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
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

    public static User createNew(String email, String firstName, String lastName) {
        return new User(UUID.randomUUID(), email, firstName, lastName);
    }
}
