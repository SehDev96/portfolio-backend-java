package com.portfolio.javabackend.service.appuser;

import com.portfolio.javabackend.model.AppResetPasswordModel;
import com.portfolio.javabackend.model.AppUser;

import java.util.List;
import java.util.Optional;

public interface AppUserService {

    AppUser saveUser(AppUser user);

    AppUser updateUser(AppUser user);

    void deleteUser(Long userid);

    AppUser findByUsername(String username);

    AppUser findByEmail(String email);

    AppUser findByUserId(Long userId);

    List<AppUser> findAllUsers();

    Long numberOfUsers();

    AppUser findByUsernameOrEmail(String username);

    Optional<String> getUserFullName(String username);

    Long getUserId(String username);

    void savePasswordResetToken(Long user_id, String token);

    AppResetPasswordModel getAppResetPasswordModelByToken(String token);
}
