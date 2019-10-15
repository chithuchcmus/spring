package com.example.demo;

import com.example.demo.model.Person;
import java.util.*;
import org.springframework.stereotype.Component;

@Component
public interface PersonDao {
    Person getPersonByFirstname(String firstName);

    List<Person> getAllPersons();

}
