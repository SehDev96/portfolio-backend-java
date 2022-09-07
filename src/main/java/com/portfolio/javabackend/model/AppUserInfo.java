package com.portfolio.javabackend.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "app_user_info")
public class AppUserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_info_id;

    @Column(name = "user_id")
    private Long user_id;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phone_number;
}
