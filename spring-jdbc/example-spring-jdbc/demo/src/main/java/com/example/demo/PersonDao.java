package com.example.demo;

import com.example.demo.model.Person;
import java.util.*;
import org.springframework.stereotype.Component;

@Component
public interface PersonDao {
    public List<Person> getPersonByFirstname(String firstName);
    public List<Person> getPersonByLastName(String firstName);
    public List<Person> getAllPersons();
    public Person getPersonByAge(int age);
    public int savePerson(Person person);
    public int updatePersonByLastName(String lastName, Person person);
    public int deletePersonByAge(Integer age);

}
