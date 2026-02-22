package com.study.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonDto {

    @NotBlank(message = "name is required field")
    private String firstName;

    @NotBlank(message = "name is required field")
    private String lastName;

    private String middleName;

    private boolean visible;
}