package com.example.demo;

import com.example.demo.model.Person;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class PersonDAOImpl implements PersonDao {

    private JdbcTemplate jdbcTemplate;

    private final String SQL_FIND_PERSON_BY_AGE = "select * from persons where age  = ?";
    private final String SQL_FIND_PERSON_BY_FIRST_NAME = "select * from persons where first_name  = ?";
    private final String SQL_FIND_PERSON_BY_LAST_NAME = "select * from persons where last_name  = ?";
    private final String SQL_GET_ALL_PERSON = "select * from persons";
    private final String SQL_UPDATE_PERSON = "update persons set age = ?, last_name = ?,  first_name = ? where last_name = ?";
    private final String SQL_DELETE_PERSON_BY_AGE = "delete from persons where age = ?";
    private final String SQL_INSERT_NEW_PERSON = "insert into persons(first_name,last_name,age) values (?, ?,?)";

    @Autowired
    public PersonDAOImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Person> getPersonByFirstname(String firstName) {
        return jdbcTemplate.query(SQL_FIND_PERSON_BY_FIRST_NAME, new Object[]{firstName}, new PersonMapper());
    }


    @Override
    public List<Person> getPersonByLastName(String lastName) {
        return jdbcTemplate.query(SQL_FIND_PERSON_BY_LAST_NAME, new Object[]{lastName}, new PersonMapper());
    }

    @Override
    public List<Person> getAllPersons() {
        return jdbcTemplate.query(SQL_GET_ALL_PERSON, new PersonMapper());
    }

    @Override
    public Person getPersonByAge(int age) {
        return jdbcTemplate.query(SQL_FIND_PERSON_BY_AGE,new Object[]{age},new PersonMapper()).get(0);
    }

    @Override
    public int savePerson(Person person) {
        return  jdbcTemplate.update(SQL_INSERT_NEW_PERSON,person.getFirstName(),person.getLastName(),person.getAge());
    }

    @Override
    public int updatePersonByLastName(String lastName, Person person) {
        return jdbcTemplate.update(SQL_UPDATE_PERSON, person.getAge(), person.getLastName(), person.getFirstName(), lastName);
    }


    @Override
    public int deletePersonByAge(Integer age) {
        return jdbcTemplate.update(SQL_DELETE_PERSON_BY_AGE, age);
    }
}
