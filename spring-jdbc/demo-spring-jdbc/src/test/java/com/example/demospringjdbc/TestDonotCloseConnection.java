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
        Connection beyondLimitConnection = null;
        int maxConnectionToDb = 5;
        for (int i = 1; i <= maxConnectionToDb; i++) {
            connection = null;
            connection = DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/test", "root", "1234");
            System.out.println(connection);
        }
        try {
            //when
            beyondLimitConnection = DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/test", "root", "1234");
        } catch (SQLException e) {
            //then
            Assert.assertEquals("Data source rejected establishment of connection,  message from server: \"Too many connections\"",
                    e.getMessage());
        }
    }
}
