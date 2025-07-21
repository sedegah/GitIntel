package com.githubinsights;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class GitHubService {

    private static final String API_URL = "https://api.github.com/users/";
    private final String token;

    public GitHubService(String token) {
        this.token = token;
    }

    public Set<String> getFollowers(String username) {
        JSONArray jsonArray = fetchPaginated(API_URL + username + "/followers");
        return extractUsernames(jsonArray);
    }

    public Set<String> getFollowing(String username) {
        JSONArray jsonArray = fetchPaginated(API_URL + username + "/following");
        return extractUsernames(jsonArray);
    }

    private JSONArray fetchPaginated(String apiUrl) {
        JSONArray result = new JSONArray();
        int page = 1;

        try {
            while (true) {
                HttpURLConnection conn = (HttpURLConnection) new URL(apiUrl + "?per_page=100&page=" + page).openConnection();
                if (token != null && !token.isEmpty()) {
                    conn.setRequestProperty("Authorization", "token " + token);
                }
                conn.setRequestProperty("Accept", "application/vnd.github+json");
                conn.setRequestMethod("GET");

                int responseCode = conn.getResponseCode();
                if (responseCode != 200) {
                    System.out.println("GitHub API returned status: " + responseCode);
                    return null;
                }

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
                in.close();

                JSONArray pageArray = new JSONArray(response.toString());
                if (pageArray.length() == 0) break;

                for (int i = 0; i < pageArray.length(); i++) {
                    result.put(pageArray.get(i));
                }

                page++;
            }
        } catch (Exception e) {
            System.out.println("Error fetching data from GitHub: " + e.getMessage());
            return null;
        }

        return result;
    }

    private Set<String> extractUsernames(JSONArray jsonArray) {
        Set<String> usernames = new HashSet<>();
        if (jsonArray == null) return usernames;

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject user = jsonArray.getJSONObject(i);
            usernames.add(user.optString("login"));
        }
        return usernames;
    }
}
