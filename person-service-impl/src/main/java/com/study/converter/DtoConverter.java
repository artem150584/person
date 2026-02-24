package com.study.converter;

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

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

public class DtoConverter {
    public static PersonRs mapToPersonRs(Person person) {
        return PersonRs.builder()
                .id(person.getId())
                .person(PersonDto.builder()
                        .firstName(person.getFirstName())
                        .lastName(person.getLastName())
                        .middleName(person.getMiddleName())
                        .age(person.getAge())
                        .visible(person.isVisible())
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
                .age(personRq.getPerson().getAge())
                .visible(personRq.getPerson().isVisible())
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
