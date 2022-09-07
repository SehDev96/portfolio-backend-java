package com.portfolio.javabackend.controller;

import com.portfolio.javabackend.model.AppUser;
import com.portfolio.javabackend.model.AppUserInfo;
import com.portfolio.javabackend.model.AppUserInfoCombined;
import com.portfolio.javabackend.model.Role;
import com.portfolio.javabackend.service.appuser.AppUserService;
import com.portfolio.javabackend.service.appuserinfo.AppUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;

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

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody AppUserInfoCombined appUserInfoCombined){
        if (appUserService.findByUsername(appUserInfoCombined.getUsername()) != null){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        AppUser appUser = new AppUser(appUserInfoCombined);
        appUser.setRole(Role.USER);
        appUser.setCreated_on(LocalDateTime.now());

        AppUserInfo appUserInfo = new AppUserInfo(appUserInfoCombined);

        appUser = appUserService.saveUser(appUser);

        appUserInfo.setUser_id(appUser.getUser_id());

        appUserInfoService.saveAppUserInfo(appUserInfo);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @GetMapping("/user/getinfo")
    public ResponseEntity<?> getInfo(Principal principal){
        String username = principal.getName();
        Long userid = appUserService.getUserId(username);
        AppUserInfo appUserInfo = new AppUserInfo();
        if(userid != null){
            appUserInfo = appUserInfoService.findByUserId(userid);
        }
        return new ResponseEntity<>(appUserInfo,HttpStatus.OK);
    }

}
