package com.example.entityrelation.dao;

import com.example.entityrelation.entities.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;


@Component
public class PostDaoImpl implements PostDao {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Post findPostById(Long id) {
        return  entityManager.find(Post.class,1L);
    }

    @Override
    public void savePost(Post post) {
        entityManager.persist(post);
    }
}
