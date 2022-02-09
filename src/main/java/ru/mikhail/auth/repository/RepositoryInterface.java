package ru.mikhail.auth.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

public interface RepositoryInterface <T> {
    T getElement(String val1, String val2);
    List<T> getAll();
    void addElement(T t);
    void deleteElement(T t);
    void modifyElement(T t, T new_t);
}
