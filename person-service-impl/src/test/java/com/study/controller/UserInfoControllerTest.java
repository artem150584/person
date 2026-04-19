package com.study.controller;

import com.study.dto.UserInfoDto;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Контроллер взаимодействия UserInfo")
public class UserInfoControllerTest extends AbstractIntegrationControllerTest {

    @Test
    @DisplayName("Получение UserInfo")
    public void getUserInfo() {

        var response = given()
                .header("Content-Type", "application/json")
                .contentType(ContentType.JSON)
                .when()
                .get("/user-info/get/10")
                .then()
                .statusCode(200)
                .extract()
                .as(UserInfoDto.class);

        assertEquals("Clementina DuBuque", response.getName(), "Name is incorrect");
        assertEquals("Moriah.Stanton", response.getUsername(), "Username is incorrect");
        assertEquals("Rey.Padberg@karina.biz", response.getEmail(), "Email is incorrect");
        assertEquals("024-648-3804", response.getPhone(), "Phone number is incorrect");
    }
}
