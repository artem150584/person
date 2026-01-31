package com.study;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/v1/public/person")
public interface PersonController {

    @PostMapping("/create")
    Person createPerson(@RequestBody @Valid Person person);

    @GetMapping("/get/{personId}")
    Person getPerson(@PathVariable("personId") UUID personId);

    @GetMapping("/get")
    Person getByParamPerson(@RequestParam UUID personId);

    @PutMapping("/update/{personId}")
    Person updatePerson(@RequestBody Person person, @PathVariable("personId") UUID personId);

    @PatchMapping("/update/fields/{personId}")
    Person patchPerson(@RequestBody Person person, @PathVariable("personId") UUID personID);

    @DeleteMapping("/delete/{personId}")
    Person deletePerson(@PathVariable("personId") UUID personId);

}
