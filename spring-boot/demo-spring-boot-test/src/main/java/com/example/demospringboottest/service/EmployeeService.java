package com.example.demospringboottest.service;

import com.example.demospringboottest.model.Employee;
import com.example.demospringboottest.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired(required = false)
    EmployeeRepository employeeRepository;

    public EmployeeService()
    {

    }

    public int countNumberOfEmployee()
    {
        return employeeRepository.findAll().size();
    }
    public Employee findEmployee(int id)
    {
        return employeeRepository.findByid(id);
    }
}
