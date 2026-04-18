package com.study.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
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
