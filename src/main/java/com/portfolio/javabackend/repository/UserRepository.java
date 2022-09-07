package com.portfolio.javabackend.repository;

import com.portfolio.javabackend.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface UserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByUsername(String username);

    @Query("select u from AppUser u where u.email= :name or u.username= :name")
    Optional<List<AppUser>> findByEmail(@Param("name") String email);
//
//    @Query("select u.username as username,u.email as email,u.name as name,u.role as role from User u where u.email= :name or u.username= :name")
//    Optional<List<Map<String, Object>>> findByEmailOrUsername(@Param("name") String username);
//
//    @Query("select u.email from User u where u.email= :email")
//    Optional<List<String>> getUserEmail(@Param("email") String email);
//
//    @Query("select u.username from User u where u.username= :username")
//    Optional<List<String>> getUserUsername(@Param("username") String username);
//
//    @Query("select u.email from User u where u.role='PHOTOGRAPHER'and u.email= :email")
//    Optional<List<String>> getPhotogEmail(@Param("email") String email);
//
//    @Query("select u.name from User u where u.username= :username")
//    Optional<String> getFullName(@Param("username") String username);
}
