package com.portfolio.javabackend.repository;

import com.portfolio.javabackend.model.AppUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AppUserInfoRepository extends JpaRepository<AppUserInfo,Long> {

    @Query("select u from AppUserInfo u where u.user_id= :userId")
    Optional<AppUserInfo> findByUserid(@Param("userId") Long userId);
}
