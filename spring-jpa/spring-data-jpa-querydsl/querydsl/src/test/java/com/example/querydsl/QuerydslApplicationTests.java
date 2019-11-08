package com.example.querydsl;

import com.example.querydsl.entity.Post;
import com.example.querydsl.entity.PostComment;
import com.example.querydsl.service.PostService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureJdbc;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureJdbc
@AutoConfigureTestDatabase
@TestPropertySource(properties = {
		"logging.level.ROOT=INFO",
		"logging.level.org.springframework.jdbc.core=DEBUG",
		"logging.level.org.springframework.transaction=TRACE"
})
class QuerydslApplicationTests {

	@Autowired
	private PostService postService;

	@Test
	void test_findPostByIdByQueryDsl() {

		//GIVE
		postService.savePost(initPostWithOneComment("1"));

		//WHEN
		Post post = postService.findPostById(1L);

		//Then
		Assert.assertEquals("1",postService.findPostById(1L).getId().toString());

	}

	@Test
	void test_findPostHaveMoreThanOneCommentByQueryDsl()
	{
		//GIVE
		postService.savePost(initPostWithOneComment("1"));
		postService.savePost(initPostWithTwoComment("2"));
		postService.savePost(initPostWithTwoComment("2"));
		//WHEN
		List<Post> posts = postService.findPostHaveMoveThanOneComment();
		//Then
		Assert.assertEquals(2,posts.size());
	}



	public Post initPostWithOneComment(String id) {
		Post post = new Post();
		post.setTitle("new post " + id);
		PostComment postComment = new PostComment();
		postComment.setReview("post" + id + " comment 1");
		postComment.setPost(post);
		post.addComment(postComment);
		return post;
	}
	public Post initPostWithTwoComment(String id) {
		Post post = new Post();
		post.setTitle("new post " + id);
		PostComment postComment1 = new PostComment();
		postComment1.setReview("post" + id + " comment 1");
		postComment1.setPost(post);
		PostComment postComment2 = new PostComment();
		postComment1.setReview("post" + id + " comment 2");
		postComment2.setPost(post);
		post.addComment(postComment1);
		post.addComment(postComment2);
		return post;
	}

}
