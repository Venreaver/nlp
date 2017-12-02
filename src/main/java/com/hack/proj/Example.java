package com.hack.proj;

import com.hack.proj.model.Post;
import com.hack.proj.preprocessing.CSVParser;
import com.hack.proj.preprocessing.Stemmer;

import java.io.IOException;
import java.util.List;

public class Example {
    public static void main(String[] args) {
        CSVParser csvParser = new CSVParser("src/main/resources/Posts.csv");
        try {
            List<Post> posts = csvParser.getAllPosts();
            Stemmer stemmer = new Stemmer("src/main/resources/stoplist.txt");
            posts.forEach(post -> System.out.println(post.getEnglishTokens(stemmer)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
