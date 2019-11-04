package com.example.JpaEntity.service;

import com.example.JpaEntity.entities.Post;
import com.example.JpaEntity.entities.PostDetail;
import com.example.JpaEntity.repository.PostDetailRepository;
import com.example.JpaEntity.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostDetailRepository postDetailRepository;

    @Override
    public Post findPostById(Long id) {
        return postRepository.findPostById(id);
    }

    @Override
    public void deletePostById(Long id) {
        postRepository.deletePostById(id);
    }

    @Override
    public void savePost(Post post) {
        postRepository.save(post);
    }

    @Override
    public PostDetail findPostDetailById(Long id) {
        return postDetailRepository.findPostDetailById(id);
    }
}
