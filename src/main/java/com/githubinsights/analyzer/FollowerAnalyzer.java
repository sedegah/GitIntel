package com.githubinsights.analyzer;

import com.githubinsights.model.AnalysisResult;
import com.githubinsights.service.GitHubService;

import java.util.HashSet;
import java.util.Set;

/**
 * Analyzes follower relationships for a GitHub user.
 * Identifies users who don't follow back and users you don't follow back.
 */
public class FollowerAnalyzer {

    private final GitHubService service;

    public FollowerAnalyzer(GitHubService service) {
        this.service = service;
    }

    /**
     * Analyzes the follower relationships for the given username.
     * @param username the GitHub username to analyze
     * @return AnalysisResult containing the analysis data
     */
    public AnalysisResult analyze(String username) {
        System.out.println("Fetching followers and following data for: " + username);
        
        Set<String> followers = service.getFollowers(username);
        Set<String> following = service.getFollowing(username);

        Set<String> notFollowingBack = new HashSet<>(following);
        notFollowingBack.removeAll(followers);

        Set<String> youDontFollowBack = new HashSet<>(followers);
        youDontFollowBack.removeAll(following);

        printSummary(followers.size(), following.size(), notFollowingBack.size(), youDontFollowBack.size());

        return new AnalysisResult(notFollowingBack, youDontFollowBack);
    }

    /**
     * Prints a summary of the analysis results.
     */
    private void printSummary(int followersCount, int followingCount, 
                             int notFollowingBackCount, int youDontFollowBackCount) {
        System.out.println("\n=== Analysis Summary ===");
        System.out.println("Total Followers: " + followersCount);
        System.out.println("Total Following: " + followingCount);
        System.out.println("Users not following you back: " + notFollowingBackCount);
        System.out.println("Users you don't follow back: " + youDontFollowBackCount);
        System.out.println("========================\n");
    }
}
