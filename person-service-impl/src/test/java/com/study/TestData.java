package com.study;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.study.dto.PersonRq;
import com.study.dto.UserInfoDto;
import lombok.SneakyThrows;

import java.io.IOException;

public class TestData {

    private static final String PERSON_RQ = "bodyRq/person-rq.json";
    private static final String USER_INFO_DTO = "bodyRs/user-info-dto.json";

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().registerModule(
            new JavaTimeModule());

    public static <T> T deserialize(String path, Class<T> clazz) throws IOException {
        return OBJECT_MAPPER.readValue(TestData.class.getClassLoader().getResource(path), clazz);
    }

    @SneakyThrows
    public static PersonRq getPersonRq() {

        return deserialize(PERSON_RQ , PersonRq.class);
    }

    @SneakyThrows
    public static UserInfoDto getUserInfoDto() {

        return deserialize(USER_INFO_DTO , UserInfoDto.class);
    }
}
