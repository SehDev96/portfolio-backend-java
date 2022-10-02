package com.portfolio.javabackend.repository;

import com.portfolio.javabackend.model.AppResetPasswordModel;
import com.portfolio.javabackend.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface AppResetPasswordRepository extends JpaRepository<AppResetPasswordModel,Long> {

    @Query("select u from AppResetPasswordModel u where u.token= :name")
    Optional<AppResetPasswordModel> findByToken(@Param("name") String token);

    @Query("select u from AppResetPasswordModel u where u.user_id= :name")
    Optional<AppResetPasswordModel> findByUserId(@Param("name") Long user_id);
}
