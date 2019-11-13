package com.example.querydsl.service;

import com.example.querydsl.entity.Post;
import com.example.querydsl.entity.PostComment;

import java.util.List;

public interface PostService {
    Post findPostById(Long id);
    List<Post> findPostHaveMoveThanOneComment();
    void savePost(Post post);
    Integer countPostHaveLessThanTwoComments();
    List<PostComment> findListPostByQuery();
    List<PostComment> findAllPostComments();

}
