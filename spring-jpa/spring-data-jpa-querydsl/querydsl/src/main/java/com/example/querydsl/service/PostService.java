package com.example.querydsl.service;

import com.example.querydsl.entity.Post;

import java.util.List;

public interface PostService {
    Post findPostById(Long id);
    List<Post> findPostHaveMoveThanOneComment();
    void savePost(Post post);

}
