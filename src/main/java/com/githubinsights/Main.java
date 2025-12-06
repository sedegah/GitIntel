package com.githubinsights;

import java.util.Set;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: mvn exec:java -Dexec.mainClass=\"com.githubinsights.Main\" -Dexec.args=\"<github-username>\"");
            return;
        }

        String username = args[0];

        String token = System.getenv("GITHUB_TOKEN");
        if (token == null || token.isEmpty()) {
            System.out.println("❌ Error: GITHUB_TOKEN environment variable not set.");
            return;
        }

        GitHubService service = new GitHubService(token);

        FollowerAnalyzer analyzer = new FollowerAnalyzer(service);
        Set<String> nonFollowers = analyzer.analyze(username);

        if (!nonFollowers.isEmpty()) {
            NonFollowersExporter.exportToCSV(username, nonFollowers);
        }
    }
}
