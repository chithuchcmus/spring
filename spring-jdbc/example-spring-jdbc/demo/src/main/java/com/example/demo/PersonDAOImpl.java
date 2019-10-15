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

    private final String SQL_FIND_PERSON = "select * from persons where first_name  = ?";
    private final String SQL_GET_ALL = "select * from persons";

    @Autowired
    public PersonDAOImpl(DataSource dataSource)
    {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Person getPersonByFirstname(String firstName) {
        return jdbcTemplate.queryForObject(SQL_FIND_PERSON, new Object[] { firstName }, new PersonMapper());

    }

    @Override
    public List<Person> getAllPersons() {
        return jdbcTemplate.query(SQL_GET_ALL, new PersonMapper());
    }
}
