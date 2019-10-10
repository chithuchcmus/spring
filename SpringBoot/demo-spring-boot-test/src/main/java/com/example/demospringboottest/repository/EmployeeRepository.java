package com.example.demospringboottest.repository;

import com.example.demospringboottest.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository {
    List<Employee> findAll();

    Employee findByid(int id);
}
