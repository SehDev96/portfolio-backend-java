package com.portfolio.javabackend.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "app_users_info")
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

    public AppUserInfo(){};

    public AppUserInfo(AppUserInfoCombined appUserInfoCombined){
        this.address = appUserInfoCombined.getAddress();
        this.phone_number = appUserInfoCombined.getPhone_number();
    }

    @Override
    public String toString() {
        return "AppUserInfo{" +
                "user_info_id=" + user_info_id +
                ", user_id=" + user_id +
                ", address='" + address + '\'' +
                ", phone_number='" + phone_number + '\'' +
                '}';
    }
}
