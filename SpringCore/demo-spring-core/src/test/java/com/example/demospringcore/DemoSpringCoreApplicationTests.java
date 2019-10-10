package com.example.demospringcore;

import com.example.demospringcore.connectdb.ConnectDB;
import com.example.demospringcore.connectdb.ConnectMysql;
import com.example.demospringcore.connectdb.ConnectPostgreSql;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

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

		System.out.println(env.getProperty("xxx.yyy"));
		assertThat(env.getProperty("xxx.yyy"),is("2"));
		assertThat(applicationContext.getBean("connectMysql"),notNullValue());
		assertThat(connectDB,instanceOf(ConnectMysql.class));
	}

	@Test
	public void test_getValueOfPropertiesDevAndBean_whenActiveProfileProd() {
		System.out.println(env.getProperty("xxx.yyy"));
		assertThat(env.getProperty("xxx.yyy"),is("3"));
		assertThat(applicationContext.getBean("connectPostgreSql"),notNullValue());
		assertThat(connectDB,instanceOf(ConnectPostgreSql.class));

	}
}
