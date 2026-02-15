package com.study.controller;

import com.study.dto.PersonRq;
import com.study.dto.PersonRs;
import com.study.entity.Contact;
import com.study.entity.Person;
import com.study.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class PersonControllerImpl implements PersonController {
    @Override
    public PersonRs validatePersonAndPassport(String name, String passport) {
        return null;
    }

    @Override
    public PersonRs getPersonList() {
        return null;
    }

    private final PersonService personService;

    @Override
    public PersonRs createPerson(PersonRq personRq) {
        Person person = getPerson(personRq);

        Person savedPerson = personService.save(person);

        return mapToPersonRs(savedPerson);
    }

    public static Person getPerson(PersonRq personRq) {
        Person person = new Person();
        person.setName(personRq.getName());
        person.setLastName(personRq.getLastName());
        person.setPatronymicName(personRq.getPatronymicName());
//        TODO как в Rq/Rs узанть структуру классов???
//        person.setAddresses(personRq.getAddresses());
//        person.setContacts(personRq.getContacts());
//        person.setIdentityDocuments(personRq.getIdentityDocuments());

        if (personRq.getContacts() != null) {
            personRq.getContacts().forEach(c -> {
                Contact contact = new Contact();
                contact.setContactValue(c);

                contact.setPerson(person);
                person.getContacts().add(contact);
            });
        }
        return person;
    }

    @Override
    public PersonRs getPerson(UUID personId) {
        Person person = personService.getPersonById(personId);

        return mapToPersonRs(person);
    }

    private PersonRs mapToPersonRs(Person person) {
        PersonRs response = new PersonRs();
        response.setId(person.getId());
        response.setName(person.getName());
        response.setLastName(person.getLastName());

        return response;
    }

    @Override
    public PersonRs getByParamPerson(UUID personId) {
        return null;
    }

    @Override
    public PersonRs updatePerson(PersonRq person, UUID personId) {
        return null;
    }

    @Override
    public PersonRs patchPerson(PersonRq person, UUID personID) {
        return null;
    }

    @Override
    public PersonRs deletePerson(UUID personId) {
        return null;
    }
}
