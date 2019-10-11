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
public class TestSpeedConnection {

    @Test
    public void test_compareTwoConnection_WhenCloseAndNotClose() throws SQLException, ClassNotFoundException {
        //give
        long timeRunWhenCloseConnection = 0;
        long timeRunWhenDonotCloseConnection = 0;
        long startTime = 0;
        long endTime = 0;
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/test", "root", "1234");

        //when donot close connection
        startTime = System.nanoTime();
        getListStudentFromDb(connection);
        getListStudentFromDb(connection);
        endTime = System.nanoTime();
        timeRunWhenDonotCloseConnection = endTime - startTime;

        //when close connection
        startTime = System.nanoTime();
        getListStudentFromDb(connection);
        connection.close();
        connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/test", "root", "1234");
        getListStudentFromDb(connection);
        endTime = System.nanoTime();
        timeRunWhenCloseConnection = endTime - startTime;

        //then
        System.out.println("timeRunWhenCloseConnection: " + timeRunWhenCloseConnection);
        System.out.println("timeRunWhenDonotCloseConnection: " + timeRunWhenDonotCloseConnection);
        Assert.assertTrue(timeRunWhenDonotCloseConnection < timeRunWhenCloseConnection);
        connection.close();
}

    public void getListStudentFromDb(Connection connection) throws SQLException {
        try{
            Statement stmt = connection.createStatement();
            String selectSql = "SELECT * FROM students";
            ResultSet resultSet = stmt.executeQuery(selectSql);
//            while (resultSet.next()) {
//                System.out.println(resultSet.getString("user_name"));
//                System.out.println(resultSet.getString("pass_word"));
//            }
        }catch (SQLException e)
        {
            e.getMessage();
        }
    }
}
