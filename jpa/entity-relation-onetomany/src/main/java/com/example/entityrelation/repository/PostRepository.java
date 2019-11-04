package com.example.entityrelation.repository;

import com.example.entityrelation.entities.Post;
import com.example.entityrelation.entities.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository  extends JpaRepository<Post,Long> {
    Post findPostById(Long id);
    List<Post> findTop10ByComments(PostComment postComment);
}
