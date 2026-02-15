package com.study.dto;

import jakarta.validation.constraints.NotBlank;
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
public class PersonRq {

    private UUID id;

    @NotBlank(message = "name is required field")
    private String name;

    @NotBlank(message = "name is required field")
    private String lastName;

    private String patronymicName;

    private Set<String> addresses = new HashSet<>();

    private Set<String> contacts = new HashSet<>();

    private Set<String> identityDocuments = new HashSet<>();
}