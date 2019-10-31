package com.example.jpa.demo.service;

import com.example.jpa.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

@Service
public class JpaJpqlService {

    @Autowired
    private EntityManagerComponent entityManagerComponent;

    public JpaJpqlService() {

    }

    public User getUserByEmail(String email) {
        EntityManager entityManager = entityManagerComponent.getEntityManager();
        List<User> userList = entityManager.createQuery("select u from User u where u.email = :email")
                .setParameter("email", email).getResultList();
        entityManager.close();
        if (userList.size() <= 0)
            return null;
        return userList.get(0);
    }
    public void deleteUser(User user)
    {
        EntityManager entityManager = entityManagerComponent.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createQuery("delete from User u where u.id  = :id").setParameter("id",user.getId()).executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

}
