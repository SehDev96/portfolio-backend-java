package com.portfolio.javabackend.jwt;

import lombok.Data;

@Data
public class JwtLoginRequest {

    private String username;
    private String password;

    public JwtLoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public JwtLoginRequest() {
    }
}
