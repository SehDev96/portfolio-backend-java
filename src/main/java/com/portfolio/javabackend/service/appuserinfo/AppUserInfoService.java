package com.portfolio.javabackend.service.appuserinfo;

import com.portfolio.javabackend.model.AppUser;
import com.portfolio.javabackend.model.AppUserInfo;

public interface AppUserInfoService {

    AppUserInfo saveAppUserInfo(AppUserInfo appUserInfo);

    AppUserInfo updateAppUserInfo(AppUserInfo appUserInfo);

    AppUserInfo findByUserId(Long userId);

    AppUserInfo findById(Long id);
}
