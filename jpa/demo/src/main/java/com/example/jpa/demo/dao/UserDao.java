package com.example.jpa.demo.dao;

import com.example.jpa.demo.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface UserDao {
    public void saveUser(String name,String email);
    public List<User> getAllUser();
    public void deleteUser(User user);
    public User getLastUserInTable();
}
