package com.study.service.impl;

import com.study.converter.PersonMapper;
import com.study.dto.PersonRq;
import com.study.dto.PersonRs;
import com.study.entity.Person;
import com.study.enums.DocumentType;
import com.study.exception.NotFoundCrmException;
import com.study.repository.PersonRepository;
import com.study.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.study.constants.ExceptionMessages.PERSON_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    private final PersonMapper personMapper;

    @Override
    public PersonRs getPersonById(UUID id) {

        Person person = personRepository.findById(id)
                .orElseThrow(() -> new NotFoundCrmException(String.format(PERSON_NOT_FOUND, id)));

        return personMapper.toPersonRs(person);
    }

    @Override
    public PersonRs save(PersonRq personRq) {
        Person person = personMapper.toPerson(personRq);

        return personMapper.toPersonRs(personRepository.save(person));
    }

    @Override
    public boolean getPersonByFullNameAndDocument(String firstName,
                                                  String middleName,
                                                  String lastName,
                                                  String documentType,
                                                  String series) {

        return personRepository.existsByFullNameAndIdentityDocuments(firstName,
                middleName,
                lastName,
                DocumentType.valueOf(documentType),
                series);
    }

    @Override
    public PersonRs updatePerson(UUID id, PersonRq personRq) {
        Person existingPerson = personRepository.findById(id)
                .orElseThrow(() -> new NotFoundCrmException(String.format(PERSON_NOT_FOUND, id)));

        personMapper.updatePerson(personRq, existingPerson);

        return personMapper.toPersonRs(personRepository.save(existingPerson));
    }

    public Page<PersonRs> getPersonList(Pageable pageable) {
        return personRepository.findAllByVisibleTrue(pageable)
                .map(personMapper::toPersonRs);
    }
}

