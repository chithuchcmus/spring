package com.example.demospringboottest;

import com.example.demospringboottest.model.Employee;
import com.example.demospringboottest.repository.EmployeeRepository;
import com.example.demospringboottest.service.EmployeeService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.Collectors;
import java.util.stream.IntStream;



@RunWith(SpringRunner.class)
@Configuration
public class TestByConfiguration {
    @MockBean
    EmployeeRepository employeeRepository;

    @TestConfiguration
    public static class EmployeeServiceConfiguration{

        /*
        Tạo ra trong Context một Bean TodoService
         */
        @Bean
        EmployeeService employeeService(){
            return new EmployeeService();
        }
    }
    @Autowired
    EmployeeService employeeService;


    @Test
    public void test_CountEmployeeWithConfiguration()
    {
        //give
        int numberEmployee = 10;

        //when
        Mockito.when(employeeRepository.findAll())
                .thenReturn(IntStream.range(0,numberEmployee)
                .mapToObj(i -> new Employee(i,"name-"+i,"address-"+i))
                .collect(Collectors.toList()));

        //then
        Assert.assertEquals(numberEmployee, employeeService.countNumberOfEmployee());

    }
}
