package com.portfolio.javabackend.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppUserInfoCombined {

    private String name;
    private String username;
    private String password;
    private String email;
    private String phone_number;
    private String state;
    private String city;
}
