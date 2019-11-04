package com.example.JpaEntity;

import com.example.JpaEntity.entities.Post;
import com.example.JpaEntity.entities.PostComment;
import com.example.JpaEntity.entities.PostDetail;
import com.example.JpaEntity.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JpaEntityApplicationTests {

    @Autowired
    private PostService postService;

    @Test
    public void contextLoads() {
        postService.savePost(initPost());
        Post post = postService.findPostById(1L);
        post.getPostDetail().setAuthorPost("chi toai");

        postService.savePost(post);

        PostDetail postDetailFromDB = postService.findPostDetailById(1L);
        System.out.println(post.getPostDetail());
        System.out.println(post.getPostDetail().getAuthorPost());
    }

    public Post initPost() {
        Post post = new Post();
        post.setTitlePost("new post");
        PostComment postComment1 = new PostComment();
        postComment1.setReview("post comment 1");
        PostComment postComment2 = new PostComment();
        postComment2.setReview("post comment 2");
        post.addComment(postComment1);
        post.addComment(postComment2);

        PostDetail postDetail = new PostDetail();
        postDetail.setAuthorPost("chi thuc");
        postDetail.setPost(post);

        post.setPostDetail(postDetail);
        return post;
    }
}
