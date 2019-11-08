package com.example.querydsl.repository;

import com.example.querydsl.entity.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<PostComment,Long>, QuerydslPredicateExecutor<PostComment> {

}
