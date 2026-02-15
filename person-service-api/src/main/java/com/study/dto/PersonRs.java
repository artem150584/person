package com.study.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonRs {
    private UUID id;
    private String name;
    private String lastName;
    private String patronymicName;
    private Set<String> contacts = new HashSet<>();
    private Set<String> addresses = new HashSet<>();
    private Set<String> identity_documents = new HashSet<>();

}
