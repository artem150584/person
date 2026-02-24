package com.study.service.impl;

import com.study.constants.DocumentType;
import com.study.converter.DtoConverter;
import com.study.dto.PersonRq;
import com.study.dto.PersonRs;
import com.study.entity.Person;
import com.study.repository.ContactRepository;
import com.study.repository.IdentityDocumentRepository;
import com.study.repository.PersonRepository;
import com.study.service.PersonService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.study.converter.DtoConverter.getPersonFromRq;
import static com.study.converter.DtoConverter.mapToPersonRs;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;
    private final ContactRepository contactRepository;
    private final IdentityDocumentRepository identityDocumentRepository;

    @Override
    public PersonRs getPersonById(UUID id) {

        Person person = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Персона с ID " + id + " не найден"));

        return mapToPersonRs(person);
    }

    @Override
    public PersonRs save(PersonRq personRq) {
        Person person = getPersonFromRq(personRq);

        Person personSaved = personRepository.save(person);
        contactRepository.saveAll(personSaved.getContacts());
        identityDocumentRepository.saveAll(personSaved.getIdentityDocuments());
        return mapToPersonRs(personSaved);
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
    public PersonRs updatePerson(UUID personId, PersonRq person) {
        Person existingPerson = personRepository.findById(personId)
                .orElseThrow(() -> new EntityNotFoundException("Person not found"));

        Person updatedPerson = existingPerson.toBuilder()
                .firstName(person.getPerson().getFirstName())
                .lastName(person.getPerson().getLastName())
                .middleName(person.getPerson().getMiddleName())
                .visible(person.getPerson().isVisible())
                .build();

        Person saved = personRepository.save(updatedPerson);

        return mapToPersonRs(saved);
    }

    public Page<PersonRs> getPersonList(Pageable pageable) {
        Page<Person> personPage = personRepository.findAllByVisibleTrue(pageable);

        return personPage.map(DtoConverter::mapToPersonRs);
    }
}

