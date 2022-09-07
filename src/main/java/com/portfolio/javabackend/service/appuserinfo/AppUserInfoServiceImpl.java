package com.portfolio.javabackend.service.appuserinfo;

import com.portfolio.javabackend.model.AppUserInfo;
import com.portfolio.javabackend.repository.AppUserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppUserInfoServiceImpl implements AppUserInfoService{

    @Autowired
    private AppUserInfoRepository appUserInfoRepository;

    @Override
    public AppUserInfo saveAppUserInfo(AppUserInfo appUserInfo) {
        return appUserInfoRepository.save(appUserInfo);
    }

    @Override
    public AppUserInfo updateAppUserInfo(AppUserInfo appUserInfo) {
        return appUserInfoRepository.save(appUserInfo);
    }

    @Override
    public AppUserInfo findByUserId(Long userId) {
        return appUserInfoRepository.findByUserid(userId).orElse(null);
    }

    @Override
    public AppUserInfo findById(Long id) {
        return null;
    }
}
