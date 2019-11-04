package com.example.entityrelation.service;

import com.example.entityrelation.entities.Post;
import com.example.entityrelation.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    @Transactional
    public Post findPostById(Long id) {
        Post post =  postRepository.findPostById(id);
        post.getComments();
        return post;
    }

    @Override
    @Transactional
    public void savePost(Post post) {
        postRepository.save(post);
    }

    @Override
    public void deletePost(Post post) {

    }
}
