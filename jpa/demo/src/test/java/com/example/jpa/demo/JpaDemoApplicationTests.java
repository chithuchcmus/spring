package com.example.jpa.demo;

import com.example.jpa.demo.model.User;
import com.example.jpa.demo.service.JpaJpqlService;
import com.example.jpa.demo.service.JpaService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureJdbc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class JpaDemoApplicationTests {

	@Autowired
	private JpaService jpaService;

	@Autowired
	private JpaJpqlService jpaJpqlService;

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
	public void test_jpaDeleteLastUserOfDB()
	{
		//give, user of last table user on DB
		User lastUser = jpaService.getLastOfUser();

		//when, delete last User
		jpaService.deleteUser(lastUser);

		//then
		Assert.assertNotEquals(jpaService.getLastOfUser(),lastUser);

	}

	@Test
	public void test_jpaFindUserByEmailUsingJpql(){
		//give
		User user = new User("goku","goku@gmail.com");
		jpaService.saveUser(user.getName(),user.getEmail());
		//when
		User userFromDB = jpaJpqlService.getUserByEmail(user.getEmail());
		// then
		Assert.assertEquals(user.getEmail(),userFromDB.getEmail());
		Assert.assertEquals(user.getName(),userFromDB.getName());
	}

	@Test
	public void test_jpaDeleteUserByUsingJpql()
	{
		//give
		String name = "goku";
		String email = "goku@gmail.com";
		jpaService.saveUser(name,email);
		User userFromDB = jpaJpqlService.getUserByEmail(email);

		//when
		jpaJpqlService.deleteUser(userFromDB);

		//then
		User userIsDeleted = jpaJpqlService.getUserByEmail(email);
		Assert.assertNull(userIsDeleted);

	}

}
