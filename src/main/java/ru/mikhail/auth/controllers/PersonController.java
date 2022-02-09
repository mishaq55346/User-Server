package ru.mikhail.auth.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.mikhail.auth.model.Person;
import ru.mikhail.auth.repository.PersonRepository;
import ru.mikhail.auth.service.PersonService;

import java.util.stream.Collectors;

@Controller
public class PersonController {
    @Autowired
    private PersonService service;

    @PostMapping(path="/add")
    public @ResponseBody
    String addNewPerson(@RequestBody Person person) {
        service.addPerson(person);
        return "Saved";
    }

    @DeleteMapping(path="/delete")
    public @ResponseBody
    String deletePerson(@RequestParam String email,
                         @RequestParam String password) {
        service.deletePerson(email, password);
        return "Deleted";
    }

    @GetMapping(path="/get")
    public @ResponseBody
    String getPerson(@RequestParam String email,
                      @RequestParam String password) {
        return service.getPerson(email, password).toString();
    }

    @GetMapping(path="/get_all")
    public @ResponseBody
    String getAllPerson() {
        return service.getAll().stream().map(Person::toString).collect(Collectors.joining());
    }

    @PutMapping(path="/modify_password")
    public @ResponseBody
    String modifyPassword(@RequestParam String email,
                        @RequestParam String password,
                        @RequestParam String new_password) {
        service.modifyPassword(email, password, new_password);
        return "Modified password!";
    }

    @PutMapping(path="/modify_email")
    public @ResponseBody
    String modifyEmail(@RequestParam String email,
                          @RequestParam String password,
                          @RequestParam String new_email) {
        service.modifyEmail(email, password, new_email);
        return "Modified email!";
    }

    @GetMapping(path="/help")
    public @ResponseBody
    String getHelp() {
        return "" +
                "/get_all - (GET) returns all person objects." +
                "/get - (GET) returns one person. Needs in " +
                "email and password fields." +
                "/test - (GET) returns 'test success' if " +
                "everything is fine." +
                "/add - (POST) returns 'Saved' if everything " +
                "is fine. Needs in name, email and password fields." +
                "/delete - (POST) returns 'Deleted' if everything " +
                "is fine. Needs email and password fields.";
    }

    @GetMapping(path="/test")
    public @ResponseBody
    String getTest() {
        return "test success";
    }
}
