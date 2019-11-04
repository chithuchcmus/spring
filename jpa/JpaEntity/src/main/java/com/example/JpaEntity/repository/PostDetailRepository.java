package com.example.JpaEntity.repository;

import com.example.JpaEntity.entities.PostDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostDetailRepository extends JpaRepository<PostDetail,Long> {
    public PostDetail findPostDetailById(Long id);
    public void deletePostDetailById(Long id);
}
