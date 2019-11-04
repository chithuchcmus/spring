package com.example.entityrelation.dao;

import com.example.entityrelation.entities.Post;

public interface PostDao {
    Post findPostById(Long id);
    public void savePost(Post post);
}
