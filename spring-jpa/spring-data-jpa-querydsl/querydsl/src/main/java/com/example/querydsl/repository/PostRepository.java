package com.example.querydsl.repository;

import com.example.querydsl.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;


@Repository
public interface PostRepository extends JpaRepository<Post,Long>,QuerydslPredicateExecutor<Post> {


    @Query("SELECT p.id,COUNT(p.id) from Post p join PostComment pc on p.id = pc.post.id " +
            "group by p.id having count (p.id) < :number" )
     List<Tuple> countPostHaveCommentsLessThan(@Param("number") Long number);
}
