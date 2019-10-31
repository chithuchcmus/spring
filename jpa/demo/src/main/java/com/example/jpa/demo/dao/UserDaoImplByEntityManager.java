package com.example.jpa.demo.dao;

import com.example.jpa.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class UserDaoImplByEntityManager implements UserDao {

    @Autowired
    EntityManager entityManager;

    @Override
    public void saveUser(String name, String email) {
        entityManager.persist(new User(name, email));
    }

    @Override
    public List<User> getAllUser() {
        return entityManager.createNamedQuery("User.getAll", User.class).getResultList();
    }

    @Override
    public void deleteUser(User user) {
        if (user == null)
            return;
        entityManager.remove(user);
    }

    @Override
    public User getLastUserInTable() {
        List<User> userList =  entityManager.createNamedQuery("User.getAll", User.class).getResultList();
        if(userList.size() <= 0)
            return null;
        return userList.get(userList.size()-1);
    }

}
