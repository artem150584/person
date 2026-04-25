package com.study.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonRq {

    private UUID id;

    private PersonDto person;

    private Set<AddressDto> addresses;

    private Set<ContactDto> contacts;

    private boolean isVisible = true;

    private Set<IdentityDocumentDto> identityDocuments;
}