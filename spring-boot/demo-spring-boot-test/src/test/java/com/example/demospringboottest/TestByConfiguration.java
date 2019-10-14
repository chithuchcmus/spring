package com.example.demospringboottest;

import com.example.demospringboottest.model.Employee;
import com.example.demospringboottest.model.Student;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ResolvableType;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@RunWith(SpringRunner.class)
public class TestByConfiguration {

    @TestConfiguration
    public static class EmployeeServiceConfiguration {
        /*
        Tạo ra trong Context một Bean TodoService
         */
        @Bean
        Student Student() {
            return new Student();
        }
    }

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void test_onlyLoadBeanIsConfigedIntoCtx_WithTestConfiguration() {
        Student student = (Student) applicationContext.getBean(Student.class);
        try {
            // give
            // when
            Employee employee = applicationContext.getBean(Employee.class);
        }
        //then
        catch (BeansException e) {
            //don't have config, bean isn't available
            Assert.assertEquals("No qualifying bean of type 'com.example.demospringboottest.model.Employee' available",
                    e.getMessage());
        }
        //have config, is available
        Assert.assertThat(student, instanceOf(Student.class));
    }
}
