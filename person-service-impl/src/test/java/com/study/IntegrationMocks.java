package com.study;

import com.github.tomakehurst.wiremock.client.WireMock;

import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;

public class IntegrationMocks {

    public static void mockGetUserInfo() {
        WireMock.stubFor(WireMock.get("/users/10")
                        .withHeader("MyHeader", equalTo("Value"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("user-info-dto.json")));
    }
}
