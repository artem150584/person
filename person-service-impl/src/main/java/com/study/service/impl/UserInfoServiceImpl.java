package com.study.service.impl;

import com.study.client.UserInfoClient;
import com.study.dto.UserInfoDto;
import com.study.service.UserInfoService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {

    private final UserInfoClient userInfoClient;

    @Override
    public UserInfoDto getUserInfo(String series) {
        return userInfoClient.getUserInfo(series);
    }
}
