package com.example.demo;

import com.example.demo.model.Person;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringJdbcApplicationTests {

	@Autowired
	ApplicationContext applicationContext;

	@Autowired
	PersonDao personDao;

	@Test
	public void contextLoads() {
		List<Person> person = personDao.getAllPersons();
		for (Person p : person) {
			System.out.println(p.getLastName());
		}
		Assert.assertTrue(personDao instanceof PersonDAOImpl);

	}



}
