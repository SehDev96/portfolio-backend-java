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

    @Column(name = "phone_number")
    private String phone_number;

    @Column(name = "state")
    private String state;

    @Column(name = "city")
    private String city;

    public AppUserInfo(){};

    public AppUserInfo(AppUserInfoCombined appUserInfoCombined){
        this.phone_number = appUserInfoCombined.getPhone_number();
        this.state = appUserInfoCombined.getState();
        this.city = appUserInfoCombined.getCity();
    }

    @Override
    public String toString() {
        return "AppUserInfo{" +
                "user_info_id=" + user_info_id +
                ", user_id=" + user_id +
                ", phone_number='" + phone_number + '\'' +
                ", state='" + state + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
