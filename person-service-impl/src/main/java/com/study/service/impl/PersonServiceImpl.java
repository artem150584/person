package com.study.service.impl;

import com.study.client.AuthClient;
import com.study.converter.PersonMapper;
import com.study.dto.PersonRq;
import com.study.dto.PersonRs;
import com.study.entity.Credential;
import com.study.entity.IdentityDocument;
import com.study.entity.Person;
import com.study.enums.DocumentType;
import com.study.exception.NotFoundCrmException;
import com.study.repository.PersonRepository;
import com.study.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.study.constants.ExceptionMessages.PERSON_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    private final PersonMapper personMapper;

    private final AuthClient authClient;

    @Override
    public PersonRs getPersonById(UUID id) {

        Person person = personRepository.findById(id)
                .orElseThrow(() -> new NotFoundCrmException(String.format(PERSON_NOT_FOUND, id)));

        return personMapper.toPersonRs(person);
    }

    @Override
    public PersonRs save(PersonRq personRq) {
        Person person = personMapper.toPerson(personRq);

        if (person.getIdentityDocuments() != null) {
            person.getIdentityDocuments().forEach(doc -> doc.setPerson(person));
        }

        if (person.getContacts() != null) {
            person.getContacts().forEach(contact -> contact.setPerson(person));
        }

        String series = person.getIdentityDocuments().stream()
                .findFirst()
                .map(IdentityDocument::getSeries)
                .orElse("default_value");

        String newToken = authClient.getToken(series);

        person.setCredential(Credential.builder()
                .series(series)
                .token(newToken)
                .expiredDate(LocalDateTime.now().plusHours(1))
                .build());

        person.getCredential().setToken(newToken);

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

