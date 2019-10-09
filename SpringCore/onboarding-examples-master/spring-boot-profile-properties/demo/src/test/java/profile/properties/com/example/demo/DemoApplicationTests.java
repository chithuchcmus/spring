package profile.properties.com.example.demo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.DispatcherServlet;
import profile.properties.com.example.demo.Repository.ConnectDB;
import profile.properties.com.example.demo.Repository.ConnectMysql;
import profile.properties.com.example.demo.Repository.ConnectPostgreSql;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsSame.sameInstance;

public class DemoApplicationTests {

	@Test
	public void testHaveBeanDispatcherServlet()
	{
		ApplicationContext context = SpringApplication.run(DemoApplication.class);
		String beanNames = context.getBean("dispatcherServlet").toString();

	}

}
