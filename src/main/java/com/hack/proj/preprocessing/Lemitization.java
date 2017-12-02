package com.hack.proj.preprocessing;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Lemitization {


    public static List<String> tokenizeString(Analyzer analyzer, String string) {
        List<String> result = new ArrayList<String>();
        try {
            TokenStream stream = analyzer.tokenStream(null, new StringReader(string));
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
