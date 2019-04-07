package com.isep.T2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.json.simple.JSONObject;


public class ReadJsonFile {


    public static JSONObject read(String name) {
        File file = new File("src/main/resources/articlesPending/" + name);
        JSONObject article = new JSONObject();

        try {
            Scanner scanner = new Scanner(file);

            //now read the file line by line...
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                line = line.replaceAll("[\"'\u2018\u2019\u201c\u201d]", "").replaceAll("\u2014", "-");
                if (line.substring(0, 10).contains("title")) {
                    article.put("title", line.substring(7));
                }
                if (line.substring(0, 10).contains("author")) {
                    article.put("author", line.substring(8));
                }
                if (line.substring(0, 10).contains("date")) {
                    article.put("date", line.substring(6));
                }
                if (line.substring(0, 10).contains("content")) {
                    article.put("content", line.substring(9));
                }
            }

            return article;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}