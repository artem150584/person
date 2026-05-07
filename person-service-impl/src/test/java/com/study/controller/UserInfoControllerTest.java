package com.study.controller;

import com.study.dto.UserInfoDto;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.study.IntegrationMocks.mockGetUserInfo;
import static com.study.TestData.getUserInfoDto;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Контроллер взаимодействия UserInfo")
public class UserInfoControllerTest extends AbstractIntegrationControllerTest {

    @Test
    @DisplayName("Получение UserInfo")
    public void getUserInfo() {

        mockGetUserInfo();

        UserInfoDto expectedRs = getUserInfoDto();

        var response = given()
                .header("Content-Type", "application/json")
                .contentType(ContentType.JSON)
                .when()
                .get("/user-info/get/10")
                .then()
                .statusCode(200)
                .extract()
                .as(UserInfoDto.class);

        assertEquals(expectedRs.getName(), response.getName(), "Name is incorrect");
        assertEquals(expectedRs.getUsername(), response.getUsername(), "Username is incorrect");
        assertEquals(expectedRs.getEmail(), response.getEmail(), "Email is incorrect");
        assertEquals(expectedRs.getPhone(), response.getPhone(), "Phone number is incorrect");
    }
}
