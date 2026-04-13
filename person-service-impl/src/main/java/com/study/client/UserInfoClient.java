package com.study.client;

import com.study.dto.UserInfoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserInfoClient {

    private final RestTemplate restTemplate;

    @Value("${person-properties.user-info.url}")
    String url;

    public UserInfoDto getUserInfo(String id) {
        log.info("getting user info");
        HttpHeaders headers = new HttpHeaders();
        headers.add("MyHeader", "Value");

        Map<String, String> params = new HashMap<>();
        params.put("id", id);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<UserInfoDto> response = restTemplate.exchange(url, HttpMethod.GET, entity, UserInfoDto.class, params);

            return response.getBody();
        } catch (HttpClientErrorException.NotFound e) {
            return new UserInfoDto();
        }
    }
}
