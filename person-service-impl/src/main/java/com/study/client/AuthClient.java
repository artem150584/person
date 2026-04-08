package com.study.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "auth-controller-client", url = "http://localhost:8081/api/v1/auth")
public interface AuthClient { // Убрали extends AuthController

    @GetMapping("/token/{series}")
    String getToken(@PathVariable("series") String series);
}
