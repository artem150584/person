package com.study.controller;

import com.study.dto.PersonRq;
import com.study.dto.PersonRs;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@RestController
@RequestMapping("/v1/public/person")
public interface PersonController {

    @PostMapping("/create")
    PersonRs createPerson(@RequestBody @Valid PersonRq person);

    @GetMapping("/get/{personId}")
    PersonRs getPerson(@PathVariable("personId") UUID personId);

    @GetMapping("/verify")
    boolean validatePersonAndPassport(@RequestParam String firstName
            ,@RequestParam String middleName
            ,@RequestParam String lastName
            ,@RequestParam String documentType
            ,@RequestParam String series);

    @GetMapping("/get")
    PersonRs getByParamPerson(@RequestParam UUID personId);

    @GetMapping("/list")
    Page<PersonRs> getPersons(@PageableDefault(size = 10, sort = "lastName") Pageable pageable);

    @PutMapping("/update/{personId}")
    PersonRs updatePerson(@RequestBody PersonRq person, @PathVariable("personId") UUID personId);

    @PatchMapping("/update/fields/{personId}")
    PersonRs patchPerson(@RequestBody PersonRq person, @PathVariable("personId") UUID personID);

    @DeleteMapping("/delete/{personId}")
    PersonRs deletePerson(@PathVariable("personId") UUID personId);

}
