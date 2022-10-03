package com.portfolio.javabackend.controller;

import com.portfolio.javabackend.model.AppUser;
import com.portfolio.javabackend.model.AppUserInfo;
import com.portfolio.javabackend.model.AppUserInfoCombined;
import com.portfolio.javabackend.service.appuser.AppUserService;
import com.portfolio.javabackend.service.appuserinfo.AppUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/app")
@RestController
public class UserController {

    @Autowired
    AppUserService appUserService;

    @Autowired
    AppUserInfoService appUserInfoService;


    @GetMapping("/testing")
    public ResponseEntity<?> testing(){
        AppUser appUser = new AppUser();
        appUserService.saveUser(appUser);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @GetMapping("/user/getinfo")
    public ResponseEntity<?> getInfo(Principal principal){
        String username = principal.getName();
        Long userid = appUserService.getUserId(username);
        AppUserInfo appUserInfo = new AppUserInfo();
        AppUser appUser = new AppUser();
        AppUserInfoCombined appUserInfoCombined = new AppUserInfoCombined();
        if(userid != null){
            appUserInfo = appUserInfoService.findByUserId(userid);
            appUser = appUserService.findByUserId(userid);
            appUserInfoCombined.setCity(appUserInfo.getCity());
            appUserInfoCombined.setName(appUser.getName());
            appUserInfoCombined.setEmail(appUser.getEmail());
            appUserInfoCombined.setPhone_number(appUserInfo.getPhone_number());
            appUserInfoCombined.setUsername(appUser.getUsername());
            appUserInfoCombined.setState(appUserInfo.getState());
        }
        return new ResponseEntity<>(appUserInfoCombined,HttpStatus.OK);
    }

}
