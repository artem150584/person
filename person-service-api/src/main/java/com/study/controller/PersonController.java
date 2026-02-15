package com.study.controller;

import com.study.dto.PersonRq;
import com.study.dto.PersonRs;
import jakarta.validation.Valid;
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

    @GetMapping("/get/list-person")
    PersonRs getPersonList();

    @GetMapping("/verify")
    PersonRs validatePersonAndPassport(@RequestParam String name, @RequestParam String passport);

    @GetMapping("/get")
    PersonRs getByParamPerson(@RequestParam UUID personId);

    @PutMapping("/update/{personId}")
    PersonRs updatePerson(@RequestBody PersonRq person, @PathVariable("personId") UUID personId);

    @PatchMapping("/update/fields/{personId}")
    PersonRs patchPerson(@RequestBody PersonRq person, @PathVariable("personId") UUID personID);

    @DeleteMapping("/delete/{personId}")
    PersonRs deletePerson(@PathVariable("personId") UUID personId);

}
