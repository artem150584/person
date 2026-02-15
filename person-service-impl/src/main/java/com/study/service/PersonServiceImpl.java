package com.study.service;

import com.study.entity.Address;
import com.study.entity.Person;
import com.study.repository.AddressRepository;
import com.study.repository.PersonRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;
    private final AddressRepository addressRepository;

    @Override
    public Person getPersonById(UUID id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Персона с ID " + id + " не найден"));
    }

    @Override
    public Person save(Person person) {
        return personRepository.save(person);
    }

    public void linkPersonToAddress(UUID personId, Integer addressId) {
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new EntityNotFoundException("Person not found"));

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new EntityNotFoundException("Address not found"));

        person.addAddress(address);

        personRepository.save(person);
    }

}

