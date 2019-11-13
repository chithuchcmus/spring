package com.example.querydsl.service;

import com.example.querydsl.entity.Post;
import com.example.querydsl.entity.PostComment;
import com.example.querydsl.entity.QPost;
import com.example.querydsl.repository.CommentRepository;
import com.example.querydsl.repository.PostRepository;
import com.google.common.collect.Lists;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl  implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

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

    @Override
    public Integer countPostHaveLessThanTwoComments() {
//        List<Tuple> list =  postRepository.countPostHaveCommentsLessThan(2L);
//        for (Tuple l: list) {
//            System.out.println(l.get(0));
//            System.out.println(l.get(1));
//
//        }
//        return list.size();
        BooleanExpression findPostHaveMoveLessThanTwoComment = QPost.post.comments.size().lt(2);
        Iterable<Post> posts = postRepository.findAll(findPostHaveMoveLessThanTwoComment);

        if(posts != null)
        {
            return Lists.newArrayList(posts).size();
        }
        return -1;
    }

    @Override
    public List<PostComment> findListPostByQuery() {
         return commentRepository.findListPostByQuery();

    }

    @Override
    public List<PostComment> findAllPostComments() {
        PageRequest page = PageRequest.of(1,2);
        return commentRepository.findAll(page).getContent();
    }

}
