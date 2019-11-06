package com.example.springjpa.service;

import com.example.springjpa.entities.Post;

import java.util.List;

public interface PostService {
    Post findPostById(Long id);
    void savePost(Post post);
    void deletePost(Post post);
    boolean deleteAndFindPostById(Long id);
    boolean updatePostTitleById(Long id, String title);
    Post findPostByReview(String review);
    Post findFirstPostWithReviewLike(String review);
    List<Post> findPostsByTitleContaining(String title);
}
