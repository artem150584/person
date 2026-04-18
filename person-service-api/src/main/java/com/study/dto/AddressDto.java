package com.study.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDto {

    private String zipCode;

    @NotBlank(message = "name is required field")
    private String city;

    @NotBlank(message = "name is required field")
    private String street;

    @NotBlank(message = "name is required field")
    private Integer home;

    private Integer flat;
}
