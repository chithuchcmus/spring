package profile.properties.com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import profile.properties.com.example.demo.Repository.ConnectDB;
import profile.properties.com.example.demo.Repository.ConnectMysql;

@Configuration
@ComponentScan
@PropertySource("classpath:config.properties")
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
    @Autowired
    Environment environment;

    @Bean
    public ConnectDB connectDB()
    {
        ConnectDB connectDB = new ConnectMysql();
        System.out.println(environment.getProperty("xxx.yyy"));
        System.out.println(environment.getProperty("xxx.zzz"));
        System.out.println(environment.getProperty("xxx.a"));
        return connectDB;
    }
}
