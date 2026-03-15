package com.study.converter;

import com.study.dto.PersonRq;
import com.study.dto.PersonRs;
import com.study.entity.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    @Mapping(target = "person.firstName", qualifiedByName = "toUpperCase", source = "firstName")
    @Mapping(target = "person.lastName", qualifiedByName = "toUpperCase", source = "lastName")
    @Mapping(target = "person.middleName", qualifiedByName = "toUpperCase", source = "middleName")
    @Mapping(target = "person.age", source = "age")
    @Mapping(target = "person.visible", source = "visible")
    @Mapping(target = "address", source = "addresses")
    @Mapping(target = "contact", source = "contacts")
    @Mapping(target = "identityDocuments", source = "identityDocuments")
    PersonRs toPersonRs(Person person);

    @Mapping(target = ".", source = "personRq.person")
    @Mapping(target = "firstName", expression = "java(personRq.getPerson().getFirstName().toUpperCase())")
    @Mapping(target = "lastName", expression = "java(personRq.getPerson().getFirstName().toUpperCase())")
    @Mapping(target = "middleName", expression = "java(personRq.getPerson().getFirstName().toUpperCase())")
    @Mapping(target = "addresses", source = "personRq.address")
    @Mapping(target = "contacts", source = "personRq.contact")
    Person toPerson(PersonRq personRq);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = ".", source = "personRq.person")
    @Mapping(target = "visible", source = "personRq.visible")
    @Mapping(target = "addresses", source = "personRq.address")
    @Mapping(target = "contacts", source = "personRq.contact")
    void updatePerson(PersonRq personRq, @MappingTarget Person existingPerson);

    @Named("toUpperCase")
    default String toUpperCase(String firstName) {
        return firstName.toUpperCase();
    }
}
