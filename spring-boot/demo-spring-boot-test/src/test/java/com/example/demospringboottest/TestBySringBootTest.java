package com.example.demospringboottest;

import com.example.demospringboottest.model.Employee;
import com.example.demospringboottest.model.Student;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.instanceOf;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TestBySringBootTest {

	@Autowired
	ApplicationContext applicationContext;
	@Test
	public void test_SpringBootAutoLoadAllBeanIntoCtx_WhenUseSpringBootTest()
	{
		//give
		//when
		Student student = applicationContext.getBean(Student.class);
		Employee employee = applicationContext.getBean(Employee.class);
		//then
		//auto load all bean into applicationContext
		Assert.assertThat(employee,instanceOf(Employee.class));
		Assert.assertThat(student,instanceOf(Student.class));

	}
}
