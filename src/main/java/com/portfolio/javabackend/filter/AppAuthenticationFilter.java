package com.portfolio.javabackend.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.javabackend.jwt.JwtLoginRequest;
import com.portfolio.javabackend.jwt.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AppAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authManager;
    private JwtUtil jwtUtil = new JwtUtil();

    public AppAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.setFilterProcessesUrl("/app/login");
        this.authManager = authenticationManager;
    }

    public AppAuthenticationFilter() {
    }

    ;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
            throws AuthenticationException {

        JwtLoginRequest creds;
        try {
            creds = new ObjectMapper().readValue(request.getInputStream(), JwtLoginRequest.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(creds.getUsername(), creds.getPassword());
        return authManager.authenticate(authenticationToken);

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authentication) throws IOException, ServletException {
        User user = (User) authentication.getPrincipal();

        // Generate Tokens
        String accessTokens = jwtUtil.generateAccessToken(user);
        String refreshTokens = jwtUtil.generateRefreshToken(user);

        Map<String, Object> tokens = new HashMap<>();
        tokens.put("access_token", accessTokens);
        tokens.put("refresh_token", refreshTokens);
        response.setContentType("application/json");
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus((HttpServletResponse.SC_UNAUTHORIZED));

        Map<String, Object> reponseAuth = new HashMap<>();
        reponseAuth.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        reponseAuth.put("message", "You have entered an invalid username or password");
        new ObjectMapper().writeValue(response.getOutputStream(), reponseAuth);
    }
}
