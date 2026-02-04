package com.githubinsights.exporter;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

/**
 * Exports follower analysis data to CSV files.
 */
public class CSVExporter {

    private static final String OUTPUT_DIR = "output";
    private static final String FILE_EXTENSION = ".csv";

    /**
     * Exports a set of usernames to a CSV file.
     * @param filename the base filename (without extension)
     * @param usernames the set of usernames to export
     * @throws IOException if an I/O error occurs
     */
    public static void exportToCSV(String filename, Set<String> usernames) throws IOException {
        ensureOutputDirectoryExists();
        
        String fullPath = OUTPUT_DIR + "/" + filename + FILE_EXTENSION;
        
        try (FileWriter writer = new FileWriter(fullPath)) {
            writer.write("Username\n");
            for (String user : usernames) {
                writer.write(escapeCSV(user) + "\n");
            }
        }
        
        System.out.println("✓ Exported " + usernames.size() + " users to: " + fullPath);
    }

    /**
     * Ensures the output directory exists, creating it if necessary.
     */
    private static void ensureOutputDirectoryExists() throws IOException {
        Path outputPath = Paths.get(OUTPUT_DIR);
        if (!Files.exists(outputPath)) {
            Files.createDirectories(outputPath);
        }
    }

    /**
     * Escapes special characters in CSV values.
     */
    private static String escapeCSV(String value) {
        if (value == null) {
            return "";
        }
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }
}
