package com.portfolio.javabackend.handler;

import org.springframework.mail.SimpleMailMessage;

public class EmailHandler {

    private static SimpleMailMessage constructEmail(String subject, String body, String email){
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setSubject(subject);
        mail.setText(body);
        mail.setTo(email);
        mail.setFrom("automated@shutterfind.com");
        return mail;
    }

    public static SimpleMailMessage constructResetTokenEmail( String contextPath, String token, String email) {
        String url = contextPath + "/resetpassword?token="+token;
        String message = "Please click on this link to reset password. \r\n" + url + "\r\n";
        System.out.println(constructEmail("Reset Password",message,email));
        return constructEmail("ShutterFind: Reset Password",message,email);
    }
}
