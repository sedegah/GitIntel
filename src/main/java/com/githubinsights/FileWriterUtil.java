package com.githubinsights;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class FileWriterUtil {

    public static void saveAsCSV(String username, List<String> nonFollowers) throws IOException {
        try (FileWriter writer = new FileWriter(username + "_nonfollowers.csv")) {
            writer.write("Username\n");
            for (String user : nonFollowers) {
                writer.write(user + "\n");
            }
        }
    }

    public static void saveAsJSON(String username, List<String> nonFollowers) throws IOException {
        JSONObject obj = new JSONObject();
        obj.put("user", username);
        obj.put("non_followers", new JSONArray(nonFollowers));

        try (FileWriter writer = new FileWriter(username + "_nonfollowers.json")) {
            writer.write(obj.toString(4));
        }
    }
}