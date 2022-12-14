package com.portfolio.javabackend.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "app_users")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    @Column(name="name")
    private String name;

    @Column(name="username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name="email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "created_on")
    private LocalDateTime created_on;

    @Transient
    private String token;

    public AppUser(){};
    public AppUser(AppUserInfoCombined appUserInfoCombined){
        this.name = appUserInfoCombined.getName();
        this.username = appUserInfoCombined.getUsername();
        this.password = appUserInfoCombined.getPassword();
        this.email = appUserInfoCombined.getEmail();
    }


}
