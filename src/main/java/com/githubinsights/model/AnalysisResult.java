package com.githubinsights.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents the result of a GitHub follower analysis.
 * Contains information about users who don't follow back and users you don't follow back.
 */
public class AnalysisResult {

    private final Set<String> notFollowingBack;
    private final Set<String> youDontFollowBack;

    public AnalysisResult() {
        this.notFollowingBack = new HashSet<>();
        this.youDontFollowBack = new HashSet<>();
    }

    public AnalysisResult(Set<String> notFollowingBack, Set<String> youDontFollowBack) {
        this.notFollowingBack = new HashSet<>(notFollowingBack);
        this.youDontFollowBack = new HashSet<>(youDontFollowBack);
    }

    /**
     * Returns an immutable set of users who don't follow you back.
     */
    public Set<String> getNotFollowingBack() {
        return Collections.unmodifiableSet(notFollowingBack);
    }

    /**
     * Returns an immutable set of users you don't follow back.
     */
    public Set<String> getYouDontFollowBack() {
        return Collections.unmodifiableSet(youDontFollowBack);
    }
}
