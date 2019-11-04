package com.example.JpaEntity.service;

import com.example.JpaEntity.entities.Post;
import com.example.JpaEntity.entities.PostDetail;


public interface PostService {
    public Post findPostById(Long id);
    public void deletePostById(Long id);
    public void savePost(Post post);
    public PostDetail findPostDetailById(Long id);
}
