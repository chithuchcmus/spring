package com.example.JpaEntity.entities;

import javax.persistence.*;

@Entity
public class PostDetail {
    @Id
    private long id;

    private String authorPost;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Post post;

    public PostDetail()
    {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Post getPost() {
        return post;
    }

    public String getAuthorPost() {
        return authorPost;
    }

    public void setAuthorPost(String authorPost) {
        this.authorPost = authorPost;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
