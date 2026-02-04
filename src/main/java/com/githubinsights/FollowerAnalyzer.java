package com.githubinsights;

import java.util.HashSet;
import java.util.Set;

public class FollowerAnalyzer {
    private final GitHubService service;

    public FollowerAnalyzer(GitHubService service) {
        this.service = service;
    }

    public Set<String> analyze(String username) {
        Set<String> followers = service.getFollowers(username);
        Set<String> following = service.getFollowing(username);

        if (followers == null || following == null) {
            System.out.println(" Failed to fetch followers/following.");
            return new HashSet<>();
        }

        System.out.println(" Total Followers: " + followers.size());
        System.out.println("Total Following: " + following.size());

        Set<String> nonFollowers = new HashSet<>(following);
        nonFollowers.removeAll(followers);

        if (nonFollowers.isEmpty()) {
            System.out.println(" Everyone you follow follows you back!");
        } else {
            System.out.println(" Users who don’t follow you back:");
            for (String user : nonFollowers) {
                System.out.println("- " + user);
            }
        }

        return nonFollowers;
    }
}
