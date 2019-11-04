package com.example.JpaEntity.repository;

import com.example.JpaEntity.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {
    public Post findPostById(Long id);
    public void deletePostById(Long id);
}
