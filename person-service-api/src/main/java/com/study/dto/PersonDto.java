package com.study.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonDto {

    @NotBlank(message = "name is required field")
    @Pattern(regexp = "^[^0-9]*$", message = "last name must not contain digits")
    private String firstName;

    @NotBlank(message = "name is required field")
    @Pattern(regexp = "^[^0-9]*$", message = "last name must not contain digits")
    private String lastName;

    private String middleName;

    @NotNull(message = "age is required field")
    @Range(min = 0, max = 120, message = "age must be between 0 and 120")
    private Integer age;

    private boolean visible;
}