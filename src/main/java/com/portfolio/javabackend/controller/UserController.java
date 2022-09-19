package com.portfolio.javabackend.controller;

import com.portfolio.javabackend.model.AppUser;
import com.portfolio.javabackend.model.AppUserInfo;
import com.portfolio.javabackend.model.AppUserInfoCombined;
import com.portfolio.javabackend.model.Role;
import com.portfolio.javabackend.model.wrapper.AppUserWrapper;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody AppUserInfoCombined appUserInfoCombined){
        if (appUserService.findByUsername(appUserInfoCombined.getUsername()) != null){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        AppUserWrapper appUserWrapper = new AppUserWrapper(appUserInfoCombined);
        appUserService.saveUser(appUserWrapper.getAppUser());

        appUserWrapper.getAppUserInfo().setUser_id(appUserWrapper.getAppUser().getUser_id());
        appUserInfoService.saveAppUserInfo(appUserWrapper.getAppUserInfo());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/finduser")
    @ResponseBody
    public ResponseEntity<?> checkUsername(@RequestParam(name = "username") String username, @RequestParam(name = "email") String userEmail) {
        try {
            Map<String, Object> responseMap = new HashMap<>();
            boolean userFound = false;
            String field = "";

            AppUser appUser = appUserService.findByEmail(userEmail);
            if(appUser != null){
                responseMap.put("field","email");
                responseMap.put("userexists",true);
                return new ResponseEntity<>(responseMap, HttpStatus.OK);
            }

            appUser = appUserService.findByUsername(username);
            if(appUser != null){
                responseMap.put("field","username");
                responseMap.put("userexists",true);
                return new ResponseEntity<>(responseMap, HttpStatus.OK);
            }

            responseMap.put("userexists", userFound);
            responseMap.put("field", field);
            return new ResponseEntity<>(responseMap, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
