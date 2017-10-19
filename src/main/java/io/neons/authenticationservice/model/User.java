package io.neons.authenticationservice.model;

import java.util.UUID;

@SuppressWarnings("unused")
public class User {
    private UUID id;
    public String username;
    public String password;
    public String email;
    public Boolean isEnabled;
}
