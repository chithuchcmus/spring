package com.example.demospringjdbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.*;

@SpringBootApplication
public class DemoSpringJdbcApplication {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		SpringApplication.run(DemoSpringJdbcApplication.class, args);
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/test", "root", "1234");
		Statement stmt = con.createStatement();
		String selectSql = "SELECT * FROM students";
		ResultSet resultSet = stmt.executeQuery(selectSql);
		while (resultSet.next()) {
			System.out.println(resultSet.getString("user_name"));
			System.out.println(resultSet.getString("pass_word"));
		}
		con.close();
	}

}
