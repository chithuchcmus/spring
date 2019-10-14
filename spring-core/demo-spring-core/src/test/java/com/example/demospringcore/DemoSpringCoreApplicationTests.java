package com.example.demospringcore;

import com.example.demospringcore.connectdb.ConnectDB;
import com.example.demospringcore.connectdb.ConnectMysql;
import com.example.demospringcore.connectdb.ConnectPostgreSql;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class DemoSpringCoreApplicationTests {

	@Autowired
	ConnectDB connectDB;

	@Autowired
	Environment env;

	@Autowired
	ApplicationContext applicationContext;
	@Test
	public void test_getValueOfPropertiesDevAndBean_whenActiveProfileDev() {

		//give and when: in application.properties and @activeprofiles("dev")
		//give: set @profile in connectMysql
		//then
		//get properties in application-dev.properties when active dev
		assertThat(env.getProperty("xxx.yyy"),is("2"));

		//get bean connectMysql when active dev
		assertThat(applicationContext.getBean("connectMysql"),notNullValue());
		assertThat(connectDB,instanceOf(ConnectMysql.class));
	}

	@Test
	public void test_getValueOfPropertiesDevAndBean_whenActiveProfileProd() {
		//give and when: in application.properties and @activeprofiles("prod")

		//then
		//get properties in application-prod.properties when active prod
		assertThat(env.getProperty("xxx.yyy"),is("3"));

		//get bean connectPostgreSql when active prod
		assertThat(applicationContext.getBean("connectPostgreSql"),notNullValue());
		assertThat(connectDB,instanceOf(ConnectPostgreSql.class));

	}
}
