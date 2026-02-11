package org.example.messenger.dto;



import java.io.Serializable;


public record LoginRequest(
        String username,
        String password


) implements Serializable {}


