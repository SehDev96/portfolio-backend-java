package com.portfolio.javabackend.controller;

import com.portfolio.javabackend.handler.EmailHandler;
import com.portfolio.javabackend.model.AppResetPasswordModel;
import com.portfolio.javabackend.model.AppUser;
import com.portfolio.javabackend.model.AppUserInfoCombined;
import com.portfolio.javabackend.model.wrapper.AppUserWrapper;
import com.portfolio.javabackend.service.appuser.AppUserService;
import com.portfolio.javabackend.service.appuserinfo.AppUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/app")
@RestController
public class AuthController {

    @Autowired
    AppUserService appUserService;

    @Autowired
    AppUserInfoService appUserInfoService;

    @Autowired
    private MailSender mailSender;

    @PostMapping("/forgotpassword")
    public ResponseEntity<?> forgotPassword(@RequestBody String email){
        try {
            // decode url encoded email address
            email = URLDecoder.decode(email, "UTF-8");
            email = email.substring(0,email.length()-1);
        } catch (Exception e){
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        AppUser appUser = appUserService.findByEmail(email);
        if (appUser != null){
            System.out.println("Found User");
            // generate token and handle reset password
            String token = UUID.randomUUID().toString();
            appUserService.savePasswordResetToken(appUser.getUser_id(), token);
            mailSender.send(EmailHandler.constructResetTokenEmail("http://localhost:3000",token,appUser.getEmail()));
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/resettokenauthentication")
    public ResponseEntity<?> resetTokenAuthentication(@RequestParam(name = "token") String token){
        AppResetPasswordModel appResetPasswordModel = appUserService.getAppResetPasswordModelByToken(token);
        if(appResetPasswordModel != null){
            System.out.println("Now: "+ System.currentTimeMillis() +" " +new Date(System.currentTimeMillis()));
            System.out.println("Expiration: "+appResetPasswordModel.getExpiration() +" "+new Date(appResetPasswordModel.getExpiration()));
            int expirationInt = new Date(appResetPasswordModel.getExpiration()).compareTo(new Date(System.currentTimeMillis()));
            if(expirationInt < 0){
                // token has expired
                return new ResponseEntity<>("Token has expired",HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>(HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>("Invalid token",HttpStatus.NOT_FOUND);
        }
    }

    //@RequestMapping(value = "/resetpassword",method = RequestMethod.GET)
    @PostMapping("/resetpassword")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String,String> params){

        String token = params.get("token");
        AppResetPasswordModel appResetPasswordModel = appUserService.getAppResetPasswordModelByToken(token);
        if(appResetPasswordModel != null){
            AppUser appUser = appUserService.findByUserId(appResetPasswordModel.getUser_id());
            appUser.setPassword(params.get("key"));
            appUserService.saveUser(appUser);
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Error password reset",HttpStatus.INTERNAL_SERVER_ERROR);
        }
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

}
