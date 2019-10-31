package com.example.entityrelation.service;

import com.example.entityrelation.dao.PostDao;
import com.example.entityrelation.entities.Post;
import com.example.entityrelation.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostDao postDao;

    @Override
    @Transactional(propagation= Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class)
    public Post findPostById(Long id) {
        return postRepository.findPostById(id);
    }
    @Override
    @Transactional(propagation=Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class)
    public void savePost(Post post) {
        postRepository.save(post);
    }
}
