package com.portfolio.javabackend.model.wrapper;

import com.portfolio.javabackend.model.AppUser;
import com.portfolio.javabackend.model.AppUserInfo;
import com.portfolio.javabackend.model.AppUserInfoCombined;
import com.portfolio.javabackend.model.Role;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppUserWrapper {

    private AppUser appUser = new AppUser();
    private AppUserInfo appUserInfo = new AppUserInfo();

    public AppUserWrapper(AppUserInfoCombined appUserInfoCombined) {
        this.appUser.setName(appUserInfoCombined.getName());
        this.appUser.setEmail(appUserInfoCombined.getEmail());
        this.appUser.setUsername(appUserInfoCombined.getUsername());
        this.appUser.setPassword(appUserInfoCombined.getPassword());
        this.appUser.setRole(Role.USER);
        this.appUser.setCreated_on(LocalDateTime.now());

        this.appUserInfo.setPhone_number(appUserInfoCombined.getPhone_number());
        this.appUserInfo.setState(appUserInfoCombined.getState());
        this.appUserInfo.setCity(appUserInfoCombined.getCity());
    }
}
