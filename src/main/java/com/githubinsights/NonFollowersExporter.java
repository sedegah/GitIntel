package com.githubinsights;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

public class NonFollowersExporter {
    public static void exportToCSV(String username, Set<String> nonFollowers) {
        String filename = username + "_non_followers.csv";

        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("Non-Followers\n");
            for (String user : nonFollowers) {
                writer.write(user + "\n");
            }
            System.out.println(" Non-followers exported to: " + filename);
        } catch (IOException e) {
            System.out.println(" Failed to export non-followers: " + e.getMessage());
        }
    }
}
