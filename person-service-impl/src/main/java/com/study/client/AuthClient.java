package com.study.client;


import com.study.controller.AuthController;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "auth-controller-client", url = "http://localhost:8081")
public interface AuthClient extends AuthController {

}
