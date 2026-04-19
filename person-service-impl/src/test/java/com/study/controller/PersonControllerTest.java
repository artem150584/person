package com.study.controller;

import com.study.dto.AddressDto;
import com.study.dto.ContactDto;
import com.study.dto.IdentityDocumentDto;
import com.study.dto.PersonRq;
import com.study.dto.PersonRs;
import com.study.repository.PersonRepository;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

import static com.study.TestData.getPersonRq;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Контроллер взаимодействия Person")
public class PersonControllerTest extends AbstractIntegrationControllerTest{

    @SpyBean
    private PersonRepository personRepository;


    @Test
    @DisplayName("Создание Person")
    @Transactional
    public void createPerson() {

        PersonRq request = getPersonRq();

        var response = given()
                .header("Content-Type", "customHeader")
                .contentType(ContentType.JSON)
                .body(getPersonRq())
                .when()
                .post("/v1/public/person/create")
                .then()
                .statusCode(200)
                .extract()
                .as(PersonRs.class);

        var savedPerson = personRepository.findById(response.getId())
                .orElseThrow(() -> new AssertionError("Person not found in DB"));

        assertEquals(request.getPerson().getFirstName().toUpperCase(), savedPerson.getFirstName(), "Saved name is incorrect");
        assertEquals(request.getPerson().getLastName().toUpperCase(), savedPerson.getLastName(), "Saved last is incorrect");
        assertEquals(request.getPerson().getMiddleName().toUpperCase(), savedPerson.getMiddleName(), "Saved middle is incorrect");
        assertEquals(request.getPerson().getAge(), savedPerson.getAge(), "Saved age is incorrect");

        var contactsDb = savedPerson.getContacts().stream()
                .map(contact -> ContactDto.builder()
                        .phoneNumber(contact.getPhoneNumber())
                        .email(contact.getEmail())
                        .build())
                .collect(Collectors.toSet());
        assertEquals(request.getContacts(), contactsDb, "Contacts sets are not equal");

        var addressesReq = request.getAddresses();
        var addressesDbMapped = savedPerson.getAddresses().stream()
                .map(address -> AddressDto.builder()
                        .zipCode(address.getZipCode())
                        .city(address.getCity())
                        .street(address.getStreet())
                        .home(address.getHome())
                        .flat(address.getFlat())
                        .build())
                .collect(Collectors.toSet());
        assertEquals(addressesReq, addressesDbMapped, "Addresses data sets are not equal");

        var docsReq = request.getIdentityDocuments();
        var docsDbMapped = savedPerson.getIdentityDocuments().stream()
                .map(doc -> IdentityDocumentDto.builder()
                        .type(String.valueOf(doc.getType()))
                        .series(doc.getSeries())
                        .build())
                .collect(Collectors.toSet());
        assertEquals(docsReq, docsDbMapped, "Identity documents are not equal");
    }
}
