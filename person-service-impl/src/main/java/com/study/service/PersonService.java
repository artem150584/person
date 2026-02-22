package com.study.service;

import com.study.dto.PersonRq;
import com.study.dto.PersonRs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface PersonService {
    PersonRs getPersonById(UUID id);

    PersonRs save(PersonRq person);

    boolean getPersonByFullNameAndDocument(String firstName,
                                           String middleName,
                                           String lastName,
                                           String documentType,
                                           String series);

    PersonRs updatePerson(UUID personId, PersonRq person);

    Page<PersonRs> getPersonList(Pageable pageable);
}

