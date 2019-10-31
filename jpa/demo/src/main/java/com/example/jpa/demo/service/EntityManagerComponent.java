package com.example.jpa.demo.service;

import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

@Component
public class EntityManagerComponent {
    private EntityManagerFactory entityManagerFactory;


    public EntityManagerComponent()
    {
        entityManagerFactory = Persistence.createEntityManagerFactory("jpa");
    }

    public EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }


}
