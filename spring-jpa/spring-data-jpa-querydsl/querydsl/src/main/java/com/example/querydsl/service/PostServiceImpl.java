package com.example.querydsl.service;

import com.example.querydsl.entity.Post;
import com.example.querydsl.entity.QPost;
import com.example.querydsl.entity.QPostComment;
import com.example.querydsl.repository.PostRepository;
import com.google.common.collect.Lists;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl  implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public Post findPostById(Long id) {
        BooleanExpression findPostById = QPost.post.id.eq(id);
        Optional<Post> post = postRepository.findOne(findPostById);
        return  post.get();
    }

    @Override
    public List<Post> findPostHaveMoveThanOneComment() {

        BooleanExpression findPostHaveMoveThanOneComment = QPost.post.comments.size().goe(2);
        Iterable<Post> posts = postRepository.findAll(findPostHaveMoveThanOneComment);

        if(posts != null)
        {
            return Lists.newArrayList(posts);
        }
        return null;
    }

    @Override
    public void savePost(Post post) {
        postRepository.save(post);
    }
}
