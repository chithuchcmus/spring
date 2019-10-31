package com.example.entityrelation;

import com.example.entityrelation.entities.Post;
import com.example.entityrelation.entities.PostComment;
import com.example.entityrelation.service.PostService;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureJdbc;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.stream.events.Comment;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureJdbc
@AutoConfigureTestDatabase
@TestPropertySource(properties = {
		"logging.level.ROOT=INFO",
		"logging.level.org.springframework.jdbc.core=DEBUG",
		"logging.level.org.springframework.transaction=TRACE"
})
class EntityRelationApplicationTests {

	@Autowired
	private PostService postService;

	@Test
	void test_CreateAndSavePost() {
		//give, create post
		Post post = initPost();
		//when save post
		postService.savePost(post);
		//then
		Post postFromDb = postService.findPostById(1L);
		List<PostComment> postCommentList = postFromDb.getComments();
		for (PostComment postComment : postCommentList)
		{
			System.out.println(postComment.getReview());
		}
		System.out.println(postFromDb.getTitle());
		System.out.println(postFromDb.getId());

	}
	public Post initPost()
	{
		Post post = new Post();
		post.setTitle("new post");
		PostComment postComment1 = new PostComment();
		postComment1.setReview("post comment 1");
		PostComment postComment2 = new PostComment();
		postComment2.setReview("post comment 2");
		post.addComment(postComment1);
		post.addComment(postComment2);
		return post;
	}

}
