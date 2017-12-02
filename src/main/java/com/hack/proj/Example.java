package com.hack.proj;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.jsoup.Jsoup;

import java.io.*;
import java.util.*;

public class Example {
    public static void main(String[] args) throws IOException {
        Reader in = new FileReader("src/main/resources/Posts.csv");
        Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader().parse(in);
        List<Post> posts = new ArrayList<>();
        for (CSVRecord record : records) {
            String idCell = record.get("Id");
            String body = record.get("Body");
            String title = record.get("Title");
            String postTypeCell = record.get("PostTypeId");
            if (StringUtils.isNotBlank(idCell) && idCell.matches("\\d*")
                    && StringUtils.isNotBlank(body)
                    && StringUtils.isNotBlank(title)
                    && StringUtils.isNotBlank(postTypeCell)
                    && postTypeCell.matches("\\d*")) {
                idCell = idCell.trim();
                body = Jsoup.parse(body.trim()).text();
                title = title.trim();
                postTypeCell = postTypeCell.trim();
                long id = Long.parseLong(idCell);
                int typeId = Integer.parseInt(postTypeCell);
                PostType postType = typeId == 1 ? PostType.QUESTION : PostType.ANSWER;
                String relatedPostCell = postType == PostType.QUESTION
                        ? record.get("AcceptedAnswerId")
                        : record.get("ParentID");
                long relatedId = 0;
                if (StringUtils.isNotBlank(relatedPostCell) && relatedPostCell.matches("\\d*")) {
                    relatedPostCell = relatedPostCell.trim();
                    relatedId = Long.parseLong(relatedPostCell);
                }
                String tagsCell = record.get("Tags");
                Set<String> tags = new HashSet<>();
                if (StringUtils.isNotBlank(tagsCell)) {
                    tagsCell = tagsCell.replaceAll("><", ",")
                            .replaceAll("<", "")
                            .replaceAll(">", "").trim();
                    String[] split = tagsCell.split(",");
                    Collections.addAll(tags, split);
                }
                posts.add(new Post(id, body, title, tags, postType, relatedId));
            }
        }

        String pathStopwords =
                "C:\\Users\\Kseniia_Shavonina\\IdeaProjects\\nlp-hackathon\\src\\main\\resources\\stopwords.txt";

        List<String> listOfStopWords = createListOfStopWords(pathStopwords);


        // create stopwords chararrayset for creating specific english analyser
        CharArraySet stopwords = new CharArraySet(listOfStopWords, true);
        EnglishAnalyzer englishAnalyzer = new EnglishAnalyzer(stopwords);


        String testString = posts.get(0).getBody();

        List<String> tokenizedString = tokenizeString(englishAnalyzer, testString);

        for (String str : tokenizedString) {
            System.out.println(str);
        }

    }

    public static List<String> tokenizeString(Analyzer analyzer, String string) {
        List<String> result = new ArrayList<String>();
        try {
            TokenStream stream  = analyzer.tokenStream(null, new StringReader(string));
            stream.reset();
            while (stream.incrementToken()) {
                result.add(stream.getAttribute(CharTermAttribute.class).toString());
            }
        } catch (IOException e) {
            // not thrown b/c we're using a string reader...
            throw new RuntimeException(e);
        }
        return result;
    }

    private static List<String> createListOfStopWords(String path) throws IOException {
        // read stop words
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader
                (new FileInputStream
                        (path)));

        // create stop words list
        List<String> listOfStopWords = new ArrayList<>();
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            listOfStopWords.add(line);
        }

        /*for (int i = 0; i < listOfStopWords.size(); i++) {
            System.out.println(i + ": " + listOfStopWords.get(i));
        }*/

        return listOfStopWords;
    }
}
