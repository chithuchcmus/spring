package com.example.demospringcore.connectdb;


import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
public class ConnectPostgreSql  implements  ConnectDB{
    private String name;
    public ConnectPostgreSql()
    {

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void NotifyConnect() {
        System.out.println("Connect Postgre SQL");
    }
}
