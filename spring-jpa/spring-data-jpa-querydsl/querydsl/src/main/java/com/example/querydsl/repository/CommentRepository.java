package com.example.querydsl.repository;

import com.example.querydsl.entity.PostComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<PostComment,Long>, QuerydslPredicateExecutor<PostComment> {

    @Query("select pc from PostComment pc")
    List<PostComment> findListPostByQuery();

}
