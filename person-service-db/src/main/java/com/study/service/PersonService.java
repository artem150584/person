package com.study.service;

import com.study.entity.Person;

import java.util.UUID;

public interface PersonService {
    Person getPersonById(UUID id);

    Person save(Person person);
}
