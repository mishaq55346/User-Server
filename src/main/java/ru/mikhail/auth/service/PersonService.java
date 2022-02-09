package ru.mikhail.auth.service;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;
import ru.mikhail.auth.model.Person;
import ru.mikhail.auth.repository.PersonRepository;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PersonService {
    @Resource
    PersonRepository repository;

    public Person getPerson(String email, String password) {
        return repository.getElement(email, password);
    }

    public List<Person> getAll() {
        return repository.getAll();
    }

    public void addPerson(Person person) {
        repository.addElement(person);
    }

    public void deletePerson(String email, String password) {
        repository.deleteElement(repository.getElement(email, password));
    }

    public void modifyPassword(String email,
                               String password,
                               String newPassword) {
        Person oldPerson = new Person();
        oldPerson.setEmail(email);
        oldPerson.setPassword(password);
        Person newPerson = new Person();
        newPerson.setEmail(email);
        newPerson.setPassword(newPassword);
        repository.modifyElement(oldPerson,newPerson);
    }

    public void modifyEmail(String email,
                               String password,
                               String newEmail) {
        Person oldPerson = new Person();
        oldPerson.setEmail(email);
        oldPerson.setPassword(password);
        Person newPerson = new Person();
        newPerson.setEmail(newEmail);
        newPerson.setPassword(password);
        repository.modifyElement(oldPerson,newPerson);
    }
}
