package com.example.springjpa.service;

import com.example.springjpa.entities.Post;
import com.example.springjpa.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;


    @Override
    public Post findPostById(Long id) {
        return postRepository.findPostById(id);
    }

    @Override
    public void savePost(Post post) {
        postRepository.save(post);
    }

    @Override
    public void deletePost(Post post) {
        postRepository.deletePostById(post.getId());
    }

    @Override
    @Transactional
    public boolean deleteAndFindPostById(Long id) {
        Post post = postRepository.findPostById(id);
        postRepository.deletePostById(id);

        Post newPost = postRepository.findPostById(id);
        if(newPost !=null)
            return false;
        return true;
    }

    @Override
    @Transactional
    public boolean updatePostTitleById(Long id, String title) {

        postRepository.updateTitlePostById(title, id);

        Post post = postRepository.findPostById(id);

        System.out.println(post.getTitle());
        if(post.getTitle().equals(title))
            return false;
        return true;

    }

    @Override
    public Post findPostByReview(String review) {
        return postRepository.findPostByCommentsReview(review);
    }

    @Override
    public Post findFirstPostWithReviewLike(String review) {
        return postRepository.findFirst1ByCommentsReviewContaining(review);
    }

    @Override
    public List<Post> findPostsByTitleContaining(String title) {
        Sort sort = Sort.by("title").descending();
        return postRepository.findPostsByTitleContaining(title,sort);
    }
}
