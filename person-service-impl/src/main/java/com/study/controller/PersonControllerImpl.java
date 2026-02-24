package com.study.controller;

import com.study.dto.PersonRq;
import com.study.dto.PersonRs;
import com.study.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class PersonControllerImpl implements PersonController {

    private final PersonService personService;

    @Override
    public boolean validatePersonAndPassport(String firstName,
                                             String middleName,
                                             String lastName,
                                             String documentType,
                                             String series) {

        return personService.getPersonByFullNameAndDocument(firstName,
                middleName,
                lastName,
                documentType,
                series);
    }

    @Override
    public PersonRs createPerson(PersonRq personRq) {
        return personService.save(personRq);
    }

    @Override
    public PersonRs getPerson(UUID personId) {

        return personService.getPersonById(personId);
    }

    @Override
    public Page<PersonRs> getPersons(@PageableDefault(size = 10, sort = "lastName") Pageable pageable) {
        return personService.getPersonList(pageable);
    }

    @Override
    public PersonRs getByParamPerson(UUID personId) {
        return null;
    }

    @Override
    public PersonRs updatePerson(PersonRq person, UUID personId) {
        return personService.updatePerson(personId, person);
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
