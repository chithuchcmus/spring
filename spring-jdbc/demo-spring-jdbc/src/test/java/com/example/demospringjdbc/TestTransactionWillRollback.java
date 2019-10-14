package com.example.demospringjdbc;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestTransactionWillRollback {

    private Connection connection;
    private Statement statement;

    //give
    @Before
    public void prepareTest() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/test", "root", "1234");
        statement = connection.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        // set up student have user_name=thuc have pass_word = 1234
        String updateStudentQueryPrepareForTest = "update students  set pass_word = '1234' where user_name='thuc'";
        statement.executeUpdate(updateStudentQueryPrepareForTest);
        connection.setAutoCommit(false);
    }

    //when
    @Test
    public void test_TransactionWillRollbackWhenExceptionHappen() throws SQLException {
        //sql update
        String updateStudentQuerySucces = "update students  set pass_word = '12345' where user_name='thuc'";
        //sql update fail
        String updateStudentQueryFail = "update students  set pass_word = 12345 where user_name=noIntanceInDB";
        try {
            statement.executeUpdate(updateStudentQuerySucces);
            statement.executeUpdate(updateStudentQueryFail);
            //update fail  => rollback()
            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback();
            }
        }
    }

    //then
    //after rollback username and password will donot change
    @After
    public void testResult() throws SQLException {
        connection.setAutoCommit(true);
        String selectSql = "SELECT * FROM students where user_name='thuc'";
        ResultSet resultSet = null;
        resultSet = statement.executeQuery(selectSql);
        if (resultSet.next()) {
            Assert.assertTrue(resultSet.getString("user_name").equals("thuc"));
            Assert.assertTrue(resultSet.getString("pass_word").equals("1234"));
        }
        statement.close();
    }
}
