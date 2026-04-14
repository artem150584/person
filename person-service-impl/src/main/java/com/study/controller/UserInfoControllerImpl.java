package com.study.controller;

import com.study.dto.UserInfoDto;
import com.study.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserInfoControllerImpl implements UserInfoController {

    private final UserInfoService userInfoService;

    @Override
    public UserInfoDto getUserInfo(String series) {
        return userInfoService.getUserInfo(series);
    }
}
