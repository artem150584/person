package com.study.converter;

import com.study.dto.PersonRq;
import com.study.dto.PersonRs;
import com.study.entity.Person;
import org.mapstruct.AfterMapping;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.TARGET_IMMUTABLE)
public interface PersonMapper {

    @Mapping(target = "person.firstName", qualifiedByName = "toUpperCase", source = "firstName")
    @Mapping(target = "person.lastName", qualifiedByName = "toUpperCase", source = "lastName")
    @Mapping(target = "person.middleName", qualifiedByName = "toUpperCase", source = "middleName")
    @Mapping(target = "person.age", source = "age")
    @Mapping(target = "person.visible", source = "visible")
    @Mapping(target = "addresses", source = "addresses")
    @Mapping(target = "contacts", source = "contacts")
    @Mapping(target = "identityDocuments", source = "identityDocuments")
    PersonRs toPersonRs(Person person);

    @Mapping(target = ".", source = "personRq.person")
    @Mapping(target = "firstName", expression = "java(personRq.getPerson().getFirstName().toUpperCase())")
    @Mapping(target = "lastName", expression = "java(personRq.getPerson().getFirstName().toUpperCase())")
    @Mapping(target = "middleName", expression = "java(personRq.getPerson().getFirstName().toUpperCase())")
    @Mapping(target = "addresses", source = "personRq.addresses")
    @Mapping(target = "contacts", source = "personRq.contacts")
    Person toPerson(PersonRq personRq);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = ".", source = "personRq.person")
    @Mapping(target = "visible", source = "personRq.visible")
    @Mapping(target = "addresses", source = "personRq.addresses")
    @Mapping(target = "contacts", source = "personRq.contacts")
    void updatePerson(PersonRq personRq, @MappingTarget Person existingPerson);

    @Named("toUpperCase")
    default String toUpperCase(String firstName) {
        return firstName.toUpperCase();
    }

    @AfterMapping
    default void linkRelations(PersonRq personRq, @MappingTarget Person existingPerson) {
        if (existingPerson.getContacts() != null) {
            existingPerson.getContacts().forEach(contact -> contact.setPerson(existingPerson));
        }

        if (existingPerson.getIdentityDocuments() != null) {
            existingPerson.getIdentityDocuments().forEach(doc -> doc.setPerson(existingPerson));
        }
    }
}
