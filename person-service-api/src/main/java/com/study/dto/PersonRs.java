package com.study.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonRs {

    private UUID id;

    private PersonDto person;

    private Set<AddressDto> addresses;

    private Set<ContactDto> contacts;

    private Set<IdentityDocumentDto> identityDocuments;
}
