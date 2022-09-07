package com.portfolio.javabackend.service.userdetails;

import com.portfolio.javabackend.model.AppUser;
import com.portfolio.javabackend.repository.UserRepository;
import com.portfolio.javabackend.service.appuser.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<AppUser> userList = userRepository.findByEmail(username).orElse(null);
        if(userList.size()<1){
            throw new UsernameNotFoundException(username);
        }
        AppUser user = userList.get(0);
        Set<GrantedAuthority> grantedAuthoritySet = new HashSet<>();

        return new User(user.getUsername(),user.getPassword(),grantedAuthoritySet);
    }
}
