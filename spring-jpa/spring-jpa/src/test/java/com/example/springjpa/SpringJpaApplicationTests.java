package com.example.springjpa;

import com.example.springjpa.entities.Post;
import com.example.springjpa.entities.PostComment;
import com.example.springjpa.service.PostService;
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

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureJdbc
@AutoConfigureTestDatabase
@TestPropertySource(properties = {
        "logging.level.ROOT=INFO",
        "logging.level.org.springframework.jdbc.core=DEBUG",
        "logging.level.org.springframework.transaction=TRACE"
})
class SpringJpaApplicationTests {

    @Autowired
    private PostService postService;

    @Test
    void test_deleteEntityAndReFind_StillExist() {
        //GiVE new post
        postService.savePost(initPost("1"));

        //When
        boolean isDeleted = postService.deleteAndFindPostById(1L);

        //then
        Assert.assertTrue(isDeleted);
    }

    @Test
    void test_updatePostTitleWithoutModify() {
        //GiVE new post
        postService.savePost(initPost("1"));
        //when
        boolean updated = postService.updatePostTitleById(1L, "update");
        Post post = postService.findPostById(1L);

        System.out.println(post.getTitle());
        //then
        Assert.assertTrue(updated);
    }

    @Test
	void test_getPostListByTitleWithSortDec()
	{
		//GiVE new post
		postService.savePost(initPost("1"));
		postService.savePost(initPost("2"));
		//when
		List<Post> postList = postService.findPostsByTitleContaining("post");
		//then
		Assert.assertEquals("2", postList.get(0).getId().toString());
		Assert.assertEquals("1", postList.get(1).getId().toString());
	}

    @Test
    void test_findFirstPostByReviewContain() {
        //GiVE new post
        postService.savePost(initPost("1"));
        postService.savePost(initPost("2"));
        //when
        Post post = postService.findFirstPostWithReviewLike("post2");
        //then
        Assert.assertEquals("2", post.getId().toString());
    }

    @Test
    void test_findPostByNestedProperty() {
        //GiVE new post
        //when
        postService.savePost(initPost("1"));

        postService.savePost(initPost("2"));

        //then
        Post post = postService.findPostByReview("post2 comment 2");
        Assert.assertEquals("2", post.getId().toString());
    }


    public Post initPost(String id) {
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
