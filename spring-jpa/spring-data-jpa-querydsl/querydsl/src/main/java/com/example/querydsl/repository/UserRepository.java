package com.example.querydsl.repository;

import com.example.querydsl.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long>, QuerydslPredicateExecutor<User> {
    void deleteUserById(Long id);

}