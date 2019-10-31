package com.example.jpa.demo.service;

import com.example.jpa.demo.dao.UserDao;
import com.example.jpa.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JpaServiceImpl  implements JpaService{

    @Autowired
    private UserDao userDao;

    @Override
    @Transactional
    public void saveUser(String name, String email) {
        userDao.saveUser(name, email);
    }

    @Override
    public List<User> getAllUser() {
        return userDao.getAllUser();
    }

    @Override
    @Transactional
    public void deleteUser(User user) {
        userDao.deleteUser(user);
    }

    @Override
    public User getLastUser() {
        return userDao.getLastUserInTable();
    }

}
