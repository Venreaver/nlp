package com.hack.proj;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
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
    }
}
