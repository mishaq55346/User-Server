package ru.mikhail.auth.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ru.mikhail.auth.model.Person;
import ru.mikhail.auth.repository.RepositoryInterface;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class PersonRepository implements RepositoryInterface<Person> {
    @Resource
    private SessionFactory sessionFactory;

    @Override
    public Person getElement(String email, String password) {
        Session session = sessionFactory.getCurrentSession();
        Query q = session.createQuery("from Person where email= :email " + "and password= :password")
                .setParameter("email", email)
                .setParameter("password", password);
        Person p = (Person) q.list().get(0);
        return p;
    }

    @Override
    public List<Person> getAll() {
        Session session = sessionFactory.getCurrentSession();
        Query q = session.createQuery("from Person ", Person.class);
        //problem is here: "Person is not mapped"
        return (List<Person>) q.list();
    }

    @Override
    public void addElement(Person person) {
        Session session = sessionFactory.getCurrentSession();
        session.save(person);
    }

    @Transactional
    @Override
    public void modifyElement(Person oldPerson, Person newPerson) {
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("UPDATE Person set " +
                "email=:new_email, password=:new_password " +
                "where email=:old_email")
                .setParameter("new_email", newPerson.getEmail())
                .setParameter("new_password", newPerson.getPassword())
                .setParameter("old_email", oldPerson.getEmail())
                .executeUpdate();
    }
    @Transactional
    @Override
    public void deleteElement(Person person) {
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("DELETE from Person " +
                "where email=:email and password=:password")
                .setParameter("email", person.getEmail())
                .setParameter("password", person.getPassword())
                .executeUpdate();
    }
}
