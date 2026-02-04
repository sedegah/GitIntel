package com.githubinsights;

import com.githubinsights.analyzer.FollowerAnalyzer;
import com.githubinsights.exporter.CSVExporter;
import com.githubinsights.model.AnalysisResult;
import com.githubinsights.service.GitHubService;

import java.io.IOException;

/**
 * Main entry point for GitIntel - GitHub follower insights analyzer.
 */
public class Main {

    public static void main(String[] args) {
        if (args.length != 1) {
            printUsage();
            System.exit(1);
        }

        String username = args[0];

        // Load GitHub token from environment variable
        String token = System.getenv("GITHUB_TOKEN");
        if (token == null || token.isEmpty()) {
            System.err.println("Error: GITHUB_TOKEN environment variable not set.");
            System.err.println("Please set it using: export GITHUB_TOKEN=your_token_here");
            System.exit(1);
        }

        try {
            runAnalysis(username, token);
        } catch (IOException e) {
            System.err.println("Error during analysis: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Runs the follower analysis and exports results.
     */
    private static void runAnalysis(String username, String token) throws IOException {
        // Initialize services
        GitHubService service = new GitHubService(token);
        FollowerAnalyzer analyzer = new FollowerAnalyzer(service);

        // Run analysis
        AnalysisResult result = analyzer.analyze(username);

        // Export results
        if (!result.getNotFollowingBack().isEmpty()) {
            CSVExporter.exportToCSV(
                username + "_not_following_back_non_followers",
                result.getNotFollowingBack()
            );
        } else {
            System.out.println("✓ All users you follow also follow you back!");
        }

        if (!result.getYouDontFollowBack().isEmpty()) {
            CSVExporter.exportToCSV(
                username + "_you_dont_follow_back_non_followers",
                result.getYouDontFollowBack()
            );
        } else {
            System.out.println("✓ You follow back all your followers!");
        }

        System.out.println("\nAnalysis complete!");
    }

    /**
     * Prints usage instructions.
     */
    private static void printUsage() {
        System.out.println("Usage: mvn exec:java -Dexec.args=\"<github-username>\"");
        System.out.println("Example: mvn exec:java -Dexec.args=\"octocat\"");
    }
}
