package com.example.entityrelation.service;

import com.example.entityrelation.entities.Post;

public interface PostService {
    public Post findPostById(Long id);
    public void savePost(Post post);
    public void deletePost(Post post);
}
