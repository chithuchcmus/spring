package com.example.jpa.demo.service;

import com.example.jpa.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

@Service
public class JpaService {
    @Autowired
    EntityManagerComponent entityManagerComponent;



    public JpaService()
    {

    }
    public void saveUser(String name, String email)
    {
        EntityManager entityManager = entityManagerComponent.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(new User(name,email));
        entityManager.getTransaction().commit();
        entityManager.close();
    }
    public List<User> getAllUser()
    {
        EntityManager entityManager = entityManagerComponent.getEntityManager();
        List<User> userList = entityManager.createNamedQuery("User.getAll",User.class).getResultList();
        entityManager.close();
        return userList;
    }
    public void deleteUser(User user)
    {
        if(user == null)
            return;
        EntityManager entityManager = entityManagerComponent.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.remove(user);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
    public User getLastOfUser()
    {
        EntityManager entityManager = entityManagerComponent.getEntityManager();
        List<User> userList = entityManager.createNamedQuery("User.getAll",User.class).getResultList();
        entityManager.close();
        if(userList.size() == 0)
            return null;
        return userList.get(userList.size()-1);
    }
}
