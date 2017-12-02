package com.hack.proj.model;

import java.util.HashSet;
import java.util.Set;

public class Post {
    private long id;
    private String body;
    private String title;
    private Set<String> tags = new HashSet<>();
    private PostType postType;
    private long relatedPost;

    public Post() {
    }

    public Post(long id, String body, String title, Set<String> tags, PostType postType, long relatedPost) {
        this.id = id;
        this.body = body;
        this.title = title;
        this.tags = tags;
        this.postType = postType;
        this.relatedPost = relatedPost;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public PostType getPostType() {
        return postType;
    }

    public void setPostType(PostType postType) {
        this.postType = postType;
    }

    public long getRelatedPost() {
        return relatedPost;
    }

    public void setRelatedPost(long relatedPost) {
        this.relatedPost = relatedPost;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", body='" + body + '\'' +
                ", title='" + title + '\'' +
                ", tags=" + tags +
                ", postType=" + postType +
                ", relatedPost=" + relatedPost +
                '}';
    }
}
