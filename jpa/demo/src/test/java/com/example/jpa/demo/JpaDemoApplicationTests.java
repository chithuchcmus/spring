package com.example.jpa.demo;

import com.example.jpa.demo.model.User;
import com.example.jpa.demo.service.JpaService;
import com.example.jpa.demo.service.JpaServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureJdbc;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@AutoConfigureJdbc
@AutoConfigureTestDatabase
@TestPropertySource(properties = {
		"logging.level.ROOT=INFO",
		"logging.level.org.springframework.jdbc.core=DEBUG",
		"logging.level.org.springframework.transaction=TRACE"
})
@SpringBootTest
class JpaDemoApplicationTests {

	@Autowired
	private JpaService jpaService;


	@Test
	public void test_JpaSaveNewUser() {
		//give, save new user to DB
		jpaService.saveUser("chi thuc","thuc@gmail.com");
		// when, get all user from DB
		List<User> userList = jpaService.getAllUser();

		// then, check exist user have email thuc@gmail.com, user is last of list user get from db
		Assert.assertEquals("chi thuc",userList.get(userList.size()-1).getName());
	}

	@Test
	public void testJpaDeleteUser(){
		//give, save new user to DB
		jpaService.saveUser("chi thuc","thuc@gmail.com");

		// when, get user just saved and deleted it from DB
		User userFromDB = jpaService.getLastUser();
		jpaService.deleteUser(userFromDB);

		// then, check exist user have email thuc@gmail.com, user is last of list user get from db
		Assert.assertNull(jpaService.getLastUser());
	}

}
