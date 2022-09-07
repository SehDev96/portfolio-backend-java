package com.portfolio.javabackend.repository;

import com.portfolio.javabackend.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser,Long> {

    Optional<AppUser> findByUsername(String username);

    @Query("select u from AppUser u where u.email= :name or u.username= :name")
    Optional<List<AppUser>> findByEmail(@Param("name") String email);

    @Query("select u.id from AppUser u where u.username= :username")
    Optional<Long> getUserId(@Param("username") String username);
}
