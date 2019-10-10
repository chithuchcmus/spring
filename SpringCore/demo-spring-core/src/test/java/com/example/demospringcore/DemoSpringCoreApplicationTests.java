package com.example.demospringcore;

import com.example.demospringcore.connectdb.ConnectDB;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static sun.nio.cs.Surrogate.is;

@RunWith(SpringRunner.class)
@SpringBootTest

public class DemoSpringCoreApplicationTests {

	@Autowired
	ConnectDB connectDB;

	@Autowired
	Environment env;
	@Test
	public void test_getValueOfPropertiesDevAndBean_whenActiveProfileDev() {
		
	}

}
