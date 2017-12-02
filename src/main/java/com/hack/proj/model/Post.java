package com.hack.proj.model;

import com.hack.proj.preprocessing.Stemmer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.en.EnglishAnalyzer;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Post {
    private long id;
    private String body;
    private String title;
    private Set<String> tags = new HashSet<>();
    private PostType postType;
    private long relatedPost;
    private List<String> englishTokens;

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

    public List<String> getEnglishTokens(Stemmer stemmer) {
        if (englishTokens == null) {
            EnglishAnalyzer englishAnalyzer = new EnglishAnalyzer(new CharArraySet(stemmer.getStopList(), true));
            englishTokens = Stemmer.tokenizeString(englishAnalyzer, body);
        }
        return englishTokens;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return id == post.id &&
                relatedPost == post.relatedPost &&
                Objects.equals(body, post.body) &&
                Objects.equals(title, post.title) &&
                Objects.equals(tags, post.tags) &&
                postType == post.postType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, body, title, tags, postType, relatedPost);
    }
}
