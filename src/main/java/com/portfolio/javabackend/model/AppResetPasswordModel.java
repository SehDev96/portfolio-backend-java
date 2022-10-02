package com.portfolio.javabackend.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name ="app_password_reset")
public class AppResetPasswordModel {

    public static final long EXPIRATION = System.currentTimeMillis() + 60*60000; // 1 hour

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long user_id;

    private String token;

    private Long expiration;

    public AppResetPasswordModel() {
    }

    public AppResetPasswordModel(Long user_id, String token) {
        this.user_id = user_id;
        this.token = token;
        //this.expiration = System.currentTimeMillis()+ 24*60*60*1000;
        // 1 minute = ~60000 milliseconds
        this.expiration = EXPIRATION ; // 1 hour
    }
}
