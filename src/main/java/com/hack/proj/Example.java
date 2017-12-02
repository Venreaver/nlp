package com.hack.proj;

import com.hack.proj.model.Post;
import com.hack.proj.preprocessing.CSVParser;
import com.hack.proj.preprocessing.Stemmer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.en.EnglishAnalyzer;

import java.io.IOException;
import java.util.List;

public class Example {
    public static void main(String[] args) {
        CSVParser csvParser = new CSVParser("src/main/resources/Posts.csv");
        try {
            List<Post> posts = csvParser.getAllPosts();
            Stemmer stemmer = new Stemmer("src/main/resources/stoplist.txt");
            List<String> stopList = stemmer.getStopList();
            CharArraySet stopWords = new CharArraySet(stopList, true);
            EnglishAnalyzer englishAnalyzer = new EnglishAnalyzer(stopWords);

            // testing
            String testString = posts.get(0).getBody();
            List<String> tokenizedString = Stemmer.tokenizeString(englishAnalyzer, testString);
            tokenizedString.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
