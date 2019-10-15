package com.example.demospringjdbc;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.PublicKey;
import java.sql.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestSqlInjectTion {

    @Test
    public void test_SqlInjectionWithStatement() throws ClassNotFoundException, SQLException {
        //give
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/test", "root", "1234");
        String username = "1' or '1=1";
        String password = "1' or '1=1";
        String sqlInjectionQuery = "select * from students where user_name = '" + username + "' and pass_word = '" + password + "'";
        Statement statement = connection.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        ResultSet resultSet = null;
        //when
        resultSet = statement.executeQuery(sqlInjectionQuery);
        //then
        //move to first row of table and check data on first row
        if (resultSet.next()) {
            Assert.assertEquals("thuc", resultSet.getString("user_name"));
            Assert.assertEquals("1234", resultSet.getString("pass_word"));
        }
        statement.close();
        connection.close();
    }

    @Test
    public void test_SqlInjectionWithPrepareStatement_UseConcatString() throws ClassNotFoundException, SQLException {
        //give
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/test", "root", "1234");
        String username = "1' or '1=1";
        String password = "1' or '1=1";
        String sqlInjectionQuery = "select * from students where user_name = '" + username + "' and pass_word = '" + password + "'";
        PreparedStatement statement = connection.prepareStatement(sqlInjectionQuery);
        ResultSet resultSet = null;
        //when
        resultSet = statement.executeQuery(sqlInjectionQuery);
        //then
        //move to first row of table and check data on first row
        if (resultSet.next()) {
            Assert.assertEquals("thuc", resultSet.getString("user_name"));
            Assert.assertEquals("1234", resultSet.getString("pass_word"));
        }
        statement.close();
        connection.close();
    }

    @Test(expected = SQLSyntaxErrorException.class)
    public void test_SqlInjectionWithPrepareStatement() throws ClassNotFoundException, SQLException {
        //give
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/test", "root", "1234");
        String sqlInjectionQuery = "select * from students where user_name = ? and pass_word = ?";
        PreparedStatement statement = connection.prepareStatement(sqlInjectionQuery);
        statement.setString(1, "1 or 1=1");
        statement.setString(2, "1 or 1=1");
        ResultSet resultSet = null;
        //when
        resultSet = statement.executeQuery(sqlInjectionQuery);
        //then
        //after execute, it will throws SQLSyntaxErrorException
        statement.close();
        connection.close();
    }


//    @Test(expected = ExceptionB.class)
//    public void test() {
//        // GIVEN
//        boolean throwInTry = true;
//        boolean throwInFinally = true;
//
//        // WHEN
//        try {
//            try {
//                if (throwInTry) {
//                    throw new ExceptionA();
//                }
//            } catch (Exception e) {
//            } finally {
//                if (throwInFinally)
//                    throw new ExceptionB();
//            }
//        } catch (ExceptionB e) {
//            System.out.println("hello");
//        }
//    }
//
//    class ExceptionA extends RuntimeException {
//    }
//
//    class ExceptionB extends RuntimeException {
//    }
}
