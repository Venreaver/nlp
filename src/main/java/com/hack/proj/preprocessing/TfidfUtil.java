package com.hack.proj.preprocessing;

import java.util.List;

public class TfidfUtil {
    static double getTf(List<String> doc, String term) {
        double result = 0;
        for (String word : doc) {
            if (term.equalsIgnoreCase(word))
                result++;
        }
        return result / doc.size();
    }

    static double getIdf(List<List<String>> corpus, String term) {
        double n = 0;
        for (List<String> doc : corpus) {
            for (String word : doc) {
                if (term.equalsIgnoreCase(word)) {
                    n++;
                    break;
                }
            }
        }
        return Math.log(corpus.size() / n);
    }

    public static double getTfIdf(List<List<String>> corpus, List<String> doc, String term) {
        return getTf(doc, term) * getIdf(corpus, term);
    }
}
