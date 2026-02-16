package com.study.service;

import com.study.entity.Address;
import com.study.entity.Person;
import com.study.repository.AddressRepository;
import com.study.repository.PersonRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;
    private final AddressRepository addressRepository;

    public Person getPersonById(UUID id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Персона с ID " + id + " не найден"));
    }

    public Person save(Person person) {
        return personRepository.save(person);
    }
}

