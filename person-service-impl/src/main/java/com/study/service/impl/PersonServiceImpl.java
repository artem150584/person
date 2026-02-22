package com.study.service.impl;

import com.study.constants.DocumentType;
import com.study.dto.AddressDto;
import com.study.dto.ContactDto;
import com.study.dto.IdentityDocumentDto;
import com.study.dto.PersonDto;
import com.study.dto.PersonRq;
import com.study.dto.PersonRs;
import com.study.entity.Address;
import com.study.entity.Contact;
import com.study.entity.IdentityDocument;
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

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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

        return personPage.map(this::mapToPersonRs);
    }

    private PersonRs mapToPersonRs(Person person) {
        return PersonRs.builder()
                .id(person.getId())
                .person(PersonDto.builder()
                        .firstName(person.getFirstName())
                        .lastName(person.getLastName())
                        .middleName(person.getMiddleName())
                        .build())
                .address(person.getAddresses().stream()
                        .map(addr -> AddressDto.builder()
                                .zipCode(addr.getZipCode())
                                .city(addr.getCity())
                                .street(addr.getStreet())
                                .home(addr.getHome())
                                .flat(addr.getFlat())
                                .build())
                        .collect(Collectors.toSet()))
                .contact(person.getContacts().stream()
                        .map(c -> ContactDto.builder()
                                .phoneNumber(c.getPhoneNumber())
                                .email(c.getEmail())
                                .build())
                        .collect(Collectors.toSet()))
                .identityDocuments(person.getIdentityDocuments().stream()
                        .map(doc -> IdentityDocumentDto.builder()
                                .type(doc.getType().name()) // Превращаем Enum обратно в String
                                .series(doc.getSeries())
                                .build())
                        .collect(Collectors.toSet()))
                .build();
    }

    public static Person getPersonFromRq(PersonRq personRq) {
        Person person = Person.builder()
                .firstName(personRq.getPerson().getFirstName())
                .lastName(personRq.getPerson().getLastName())
                .middleName(personRq.getPerson().getMiddleName())
                .addresses(Optional.ofNullable(personRq.getAddress())
                        .orElse(Collections.emptySet())
                        .stream()
                        .map(dto -> Address.builder()
                                .zipCode(dto.getZipCode())
                                .city(dto.getCity())
                                .street(dto.getStreet())
                                .home(dto.getHome())
                                .flat(dto.getFlat())
                                .build())
                        .collect(Collectors.toSet()))
                .contacts(Optional.ofNullable(personRq.getContact())
                        .orElse(Collections.emptySet())
                        .stream()
                        .map(dto -> Contact.builder()
                                .phoneNumber(dto.getPhoneNumber())
                                .email(dto.getEmail())
                                .build())
                        .collect(Collectors.toSet()))
                .identityDocuments(Optional.ofNullable(personRq.getIdentityDocuments())
                        .orElse(Collections.emptySet())
                        .stream()
                        .map(dto -> IdentityDocument.builder()
                                .type(DocumentType.valueOf(dto.getType()))
                                .series(dto.getSeries())
                                .build())
                        .collect(Collectors.toSet()))
                .build();

        if (person.getContacts() != null) {
            person.getContacts().forEach(contact -> contact.setPerson(person));
        }

        if (person.getIdentityDocuments() != null) {
            person.getIdentityDocuments().forEach(identityDocument -> identityDocument.setPerson(person));
        }

        return person;
    }
}

