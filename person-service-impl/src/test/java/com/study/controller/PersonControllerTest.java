package com.study.controller;

import com.study.dto.PersonRs;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static com.study.TestData.getPersonRq;
import static io.restassured.RestAssured.given;

public class PersonControllerTest extends AbstractIntegrationControllerTest{

    @Test
    public void createPerson() {

        var response = given()
                .header("Content-Type", "customHeader")
                .contentType(ContentType.JSON)
                .body(getPersonRq())
                .when()
                .post("/create")
                .then()
                .statusCode(200)
                .extract()
                .as(PersonRs.class);

    }
}
