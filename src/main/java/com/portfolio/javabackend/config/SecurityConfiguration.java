package com.portfolio.javabackend.config;

import com.portfolio.javabackend.filter.AppAuthenticationFilter;
import com.portfolio.javabackend.filter.AppAuthorizationFilter;
import com.portfolio.javabackend.filter.InHouseCorsFilter;
import com.portfolio.javabackend.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.SessionManagementFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        AuthenticationManager authenticationManager = authenticationManager(http,passwordEncoder(),userDetailsService);

        http.csrf().disable();
        http.cors();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/app/login/**").permitAll();
        //http.authorizeRequests().antMatchers(HttpMethod.GET, "/app/finduser**","/app/finduser/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/app/forgotpassword","/app/forgotpassword**",
                "/app/register","/app/resetpassword").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/app/testing","/app/finduser**",
                "/app/finderuser/**","/app/resettokenauthentication").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/app/user/getinfo").hasAuthority(Role.USER.name());
        http.authenticationManager(authenticationManager);
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilterBefore(new AppAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(new InHouseCorsFilter(), SessionManagementFilter.class);
        http.addFilter(new AppAuthenticationFilter(authenticationManager));

        return http.build();
    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer(){
//        return (web) -> web.ignoring().antMatchers("/app/resetpassword",
//                "/app/register",
//                "/app/finduser**",
//                "/app/finderuser/**",
//                "/app/forgotpassword",
//                "/app/forgotpassword**"
//        );
//    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder bCryptPasswordEncoder,
                                                       UserDetailsService userDetailsService) throws  Exception{
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder)
                .and().build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
