package com.portfolio.javabackend.controller;

import com.portfolio.javabackend.model.AppUser;
import com.portfolio.javabackend.model.Role;
import com.portfolio.javabackend.service.appuser.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RequestMapping("/app")
@RestController
public class UserController {

    @Autowired
    AppUserService appUserService;

    @GetMapping("/testing")
    public ResponseEntity<?> testing(){
        AppUser appUser = new AppUser();
        appUserService.saveUser(appUser);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody AppUser appUser){
        if (appUserService.findByUsername(appUser.getUsername()) != null){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        appUser.setRole(Role.USER);
        appUser.setCreated_on(LocalDateTime.now());
        return new ResponseEntity<>(appUserService.saveUser(appUser),HttpStatus.CREATED);
    }

}
