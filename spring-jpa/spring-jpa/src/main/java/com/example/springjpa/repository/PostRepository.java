package com.example.springjpa.repository;

import com.example.springjpa.entities.Post;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
    Post findPostById(Long id);
    void deletePostById(Long id);
    List<Post> findAll();
    Post findPostByCommentsReview(String review);
    Post findFirst1ByCommentsReviewContaining(String review);
    List<Post> findPostsByTitleContaining(String title, Sort sort);

    @Modifying
    @Query("update Post p set p.title = ?1 where p.id = ?2 ")
    void  updateTitlePostById(String title, Long id);

}
