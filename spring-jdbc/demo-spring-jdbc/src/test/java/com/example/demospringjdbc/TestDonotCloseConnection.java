package com.example.demospringjdbc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Assert;

import java.sql.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestDonotCloseConnection {

    @Test
    public void test_getConnection_WhenItReachMaxConnection() throws SQLException, ClassNotFoundException {

    	//give
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = null;
        Connection lastConnection = null;
        int numberConnection = 5;
        try{
			for (int i = 1; i <= numberConnection; i++) {
				connection = null;
				connection = DriverManager
						.getConnection("jdbc:mysql://localhost:3306/test", "root", "1234");
				System.out.println(connection);
			}
			//when
			lastConnection = DriverManager
					.getConnection("jdbc:mysql://localhost:3306/test", "root", "1234");

		}
        catch (SQLNonTransientConnectionException e)
		{
			System.out.println(e.getMessage());
		}

        System.out.println(connection);
		System.out.println(lastConnection);

		//then
		//when limit connection, if we continue get conection,it will be null
		Assert.assertNull(lastConnection);

    }

}
