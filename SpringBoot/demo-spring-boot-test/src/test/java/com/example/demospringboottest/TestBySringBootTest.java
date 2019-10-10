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
		import org.springframework.boot.test.mock.mockito.MockBean;
		import org.springframework.test.context.junit4.SpringRunner;

		import java.util.stream.Collectors;
		import java.util.stream.IntStream;



@RunWith(SpringRunner.class)
@SpringBootTest
public class TestBySringBootTest {
	@MockBean
	EmployeeRepository employeeRepository;

	@Autowired
	EmployeeService employeeService;

	@Test
	public void test_CountEmployeeWithSringBootTest()
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
