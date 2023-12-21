package it.unina.dietideals24.security.response;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private long expiresIn;
}