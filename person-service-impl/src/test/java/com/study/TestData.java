package com.study;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.study.dto.PersonRq;
import com.study.dto.UserInfoDto;
import com.study.entity.Person;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.UUID;

public class TestData {

    private static final String PERSON_RQ = "bodyRq/person-rq.json";
    private static final String USER_INFO_DTO = "__files/user-info-dto.json";
    private static final String PERSON_OBJECT = "__files/person-object.json";

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().registerModule(
            new JavaTimeModule());

    public static <T> T deserialize(String path, Class<T> clazz) throws IOException {
        return OBJECT_MAPPER.readValue(TestData.class.getClassLoader().getResource(path), clazz);
    }

    public static final UUID PERSON_ID = UUID.fromString("cbf6a554-f33b-4e88-be14-92e3a7336312");

    @SneakyThrows
    public static PersonRq getPersonRq() {

        return deserialize(PERSON_RQ , PersonRq.class);
    }

    @SneakyThrows
    public static UserInfoDto getUserInfoDto() {

        return deserialize(USER_INFO_DTO , UserInfoDto.class);
    }

    @SneakyThrows
    public static Person getPerson() {

        return deserialize(PERSON_OBJECT , Person.class);
    }
}
