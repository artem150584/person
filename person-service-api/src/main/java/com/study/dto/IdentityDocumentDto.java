package com.study.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IdentityDocumentDto {

    @NotBlank(message = "name is required field")
    private String type;

    @NotBlank(message = "name is required field")
    private String series;
}
