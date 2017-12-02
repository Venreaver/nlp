package com.hack.proj;

import com.hack.proj.model.Post;
import com.hack.proj.preprocessing.CSVParser;
import com.hack.proj.preprocessing.Stemmer;

import java.io.IOException;
import java.util.*;

import static com.hack.proj.preprocessing.TfidfUtil.getTfIdf;
import static java.util.stream.Collectors.toList;

public class Example {
    public static void main(String[] args) {
        CSVParser csvParser = new CSVParser("src/main/resources/Posts.csv");
        List<Post> posts = null;
        try {
            posts = csvParser.getAllPosts();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (posts != null) {
            Stemmer stemmer = new Stemmer("src/main/resources/stoplist.txt");
            List<List<String>> corpus = posts.stream().map(p -> p.getEnglishTokens(stemmer)).collect(toList());
            Map<Post, Map<String, Double>> postMap = new HashMap<>(posts.size());
            for (Post post : posts) {
                List<String> englishTokens = post.getEnglishTokens(stemmer);
                Set<String> setTokens = new HashSet<>(englishTokens);
                Map<String, Double> indexMap = new HashMap<>(setTokens.size());
                for (String token : setTokens) {
                    indexMap.put(token, getTfIdf(corpus, englishTokens, token));
                }
                postMap.put(post, indexMap);
            }
            posts.forEach(post -> System.out.println(post.getEnglishTokens(stemmer)));
        }
    }
}
