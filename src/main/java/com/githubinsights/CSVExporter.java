package com.githubinsights;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class CSVExporter {

    public static void exportNonFollowers(String username, JSONArray users) {
        String fileName = username + "_non_followers.csv";
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.append("Username,Profile URL\n");

            for (int i = 0; i < users.length(); i++) {
                JSONObject user = users.getJSONObject(i);
                writer.append(escape(user.optString("login"))).append(",");
                writer.append(escape(user.optString("html_url"))).append("\n");
            }

            System.out.println("CSV exported: " + fileName);
        } catch (IOException e) {
            System.out.println("Failed to write CSV: " + e.getMessage());
        }
    }

    private static String escape(String text) {
        if (text == null) return "";
        return text
                .replace("\"", "\"\"")
                .replace(",", " ")
                .replaceAll("[\r\n]+", " ")
                .trim();
    }
}
