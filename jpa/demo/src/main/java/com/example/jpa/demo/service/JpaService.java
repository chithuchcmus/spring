package com.example.jpa.demo.service;

import com.example.jpa.demo.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface JpaService {
    public void saveUser(String name, String email);
    public List<User> getAllUser();
    public void deleteUser(User user);
    public User getLastUser();
}
