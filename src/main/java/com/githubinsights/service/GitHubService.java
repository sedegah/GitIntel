package com.githubinsights.service;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * Service class for interacting with the GitHub API.
 * Handles fetching followers and following data with pagination support.
 */
public class GitHubService {

    private static final String API_URL = "https://api.github.com/users/";
    private static final int PER_PAGE = 100;
    private final String token;

    public GitHubService(String token) {
        this.token = token;
    }

    /**
     * Fetches all followers for the given username.
     */
    public Set<String> getFollowers(String username) {
        JSONArray jsonArray = fetchPaginated(API_URL + username + "/followers");
        return extractUsernames(jsonArray);
    }

    /**
     * Fetches all users that the given username is following.
     */
    public Set<String> getFollowing(String username) {
        JSONArray jsonArray = fetchPaginated(API_URL + username + "/following");
        return extractUsernames(jsonArray);
    }

    /**
     * Fetches paginated data from the GitHub API.
     */
    private JSONArray fetchPaginated(String apiUrl) {
        JSONArray result = new JSONArray();
        int page = 1;

        try {
            while (true) {
                URL url = new URL(apiUrl + "?per_page=" + PER_PAGE + "&page=" + page);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/vnd.github+json");

                if (token != null && !token.isEmpty()) {
                    conn.setRequestProperty("Authorization", "Bearer " + token);
                }

                int responseCode = conn.getResponseCode();
                if (responseCode != 200) {
                    System.err.println("Warning: GitHub API returned status code " + responseCode);
                    break;
                }

                String response = readResponse(conn);
                JSONArray pageArray = new JSONArray(response);
                
                if (pageArray.isEmpty()) {
                    break;
                }

                for (int i = 0; i < pageArray.length(); i++) {
                    result.put(pageArray.get(i));
                }

                page++;
            }
        } catch (IOException e) {
            System.err.println("Error fetching data from GitHub API: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }

        return result;
    }

    /**
     * Reads the response from an HTTP connection.
     */
    private String readResponse(HttpURLConnection conn) throws IOException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        }
    }

    /**
     * Extracts usernames from a JSON array of GitHub user objects.
     */
    private Set<String> extractUsernames(JSONArray jsonArray) {
        Set<String> usernames = new HashSet<>();
        if (jsonArray == null) {
            return usernames;
        }

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject user = jsonArray.getJSONObject(i);
            String login = user.optString("login", null);
            if (login != null && !login.isEmpty()) {
                usernames.add(login);
            }
        }
        return usernames;
    }
}
