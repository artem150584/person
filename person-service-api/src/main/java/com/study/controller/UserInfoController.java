package com.study.controller;

import com.study.dto.UserInfoDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

public interface UserInfoController {

    @GetMapping("/user-info/get/{series}")
    UserInfoDto getUserInfo(@PathVariable String series);
}
