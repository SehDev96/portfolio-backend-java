package com.portfolio.javabackend.service.appuser;

import com.portfolio.javabackend.model.AppUser;
import com.portfolio.javabackend.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class AppUserServiceImpl implements AppUserService{

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public AppUser saveUser(AppUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return appUserRepository.save(user);
    }

    @Override
    public AppUser updateUser(AppUser user) {
        return null;
    }

    @Override
    public void deleteUser(Long userid) {

    }

    @Override
    public AppUser findByUsername(String username) {
        return appUserRepository.findByUsername(username).orElse(null);
    }

    @Override
    public AppUser findByEmail(String email) {
        AppUser appUser = null;
        Optional<List<AppUser>> appUserList = appUserRepository.findByEmail(email);
        List<AppUser> newAppUserList = appUserList.orElse(null);
        if(newAppUserList != null && newAppUserList.size()>0){
            appUser = newAppUserList.get(0);
        }
        return appUser;
    }

    @Override
    public AppUser findByUserId(Long userId) {
        return appUserRepository.findByUserId(userId).orElse(null);
    }

    @Override
    public List<AppUser> findAllUsers() {
        return null;
    }

    @Override
    public Long numberOfUsers() {
        return null;
    }

    @Override
    public AppUser findByUsernameOrEmail(String username) {
        return null;
    }

    @Override
    public Optional<String> getUserFullName(String username) {
        return Optional.empty();
    }

    @Override
    public Long getUserId(String username) {
        return appUserRepository.getUserId(username).orElse(null);
    }
}
