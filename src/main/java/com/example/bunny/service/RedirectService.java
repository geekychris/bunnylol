package com.example.bunny.service;

import com.example.bunny.model.Command;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import jakarta.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class RedirectService {

    private final Map<String, Command> commands = new HashMap<>();

    @PostConstruct
    public void initializeDefaultCommands() {
        // Primary services from README
        addCommand("g", "https://www.google.com/search?q={query}", "Google search");
        addCommand("yt", "https://www.youtube.com/results?search_query={query}", "YouTube search");
        addCommand("gh", "https://github.com/search?q={query}", "GitHub search");
        addCommand("so", "https://stackoverflow.com/search?q={query}", "Stack Overflow search");
        addCommand("wiki", "https://en.wikipedia.org/wiki/Special:Search?search={query}", "Wikipedia search");
        addCommand("amzn", "https://www.amazon.com/s?k={query}", "Amazon search");
        addCommand("tw", "https://twitter.com/search?q={query}", "Twitter search");
        addCommand("li", "https://www.linkedin.com/search/results/all/?keywords={query}", "LinkedIn search");
        
        // Additional search engines
        addCommand("bing", "https://www.bing.com/search?q={query}", "Bing search");
        addCommand("ddg", "https://duckduckgo.com/?q={query}", "DuckDuckGo search");

        // Additional video platforms
        addCommand("netflix", "https://www.netflix.com/search?q={query}", "Netflix search");

        // Additional social media
        addCommand("fb", "https://www.facebook.com/search/top?q={query}", "Facebook search");
        addCommand("ig", "https://www.instagram.com/explore/tags/{query}", "Instagram hashtag search");

        // Additional development tools
        addCommand("npm", "https://www.npmjs.com/search?q={query}", "NPM package search");
        addCommand("maven", "https://mvnrepository.com/search?q={query}", "Maven repository search");

        // Additional shopping
        addCommand("amz", "https://www.amazon.com/s?k={query}", "Amazon search");
        addCommand("ebay", "https://www.ebay.com/sch/i.html?_nkw={query}", "eBay search");

        // Additional reference
        addCommand("maps", "https://www.google.com/maps/search/{query}", "Google Maps search");
        
        // Direct navigation shortcuts (without query)
        addCommand("mail", "https://mail.google.com", "Gmail");
        addCommand("drive", "https://drive.google.com", "Google Drive");
        addCommand("cal", "https://calendar.google.com", "Google Calendar");
        addCommand("chat", "https://chat.openai.com", "ChatGPT");
        
        // Help command redirects to home page
        addCommand("help", "http://localhost:8080/", "Show all available commands");
    }

    public void addCommand(String shortcode, String urlPattern, String description) {
        Command command = new Command(shortcode, urlPattern, description);
        commands.put(shortcode.toLowerCase(), command);
    }

    public Optional<Command> getCommand(String shortcode) {
        return Optional.ofNullable(commands.get(shortcode.toLowerCase()));
    }

    public Map<String, Command> getAllCommands() {
        return new HashMap<>(commands);
    }

    public RedirectResult parseInput(String input) {
        // Default to Google search if input is empty
        if (input == null || input.trim().isEmpty()) {
            Command googleCommand = commands.get("g");
            return new RedirectResult(googleCommand, "");
        }

        String[] parts = input.trim().split("\\s+", 2);
        String commandStr = parts[0].toLowerCase();
        String query = parts.length > 1 ? parts[1].trim() : "";

        // Check if the command exists
        Optional<Command> commandOptional = getCommand(commandStr);
        
        // If command doesn't exist, use Google search with the whole input as query
        if (commandOptional.isEmpty()) {
            Command googleCommand = commands.get("g");
            return new RedirectResult(googleCommand, input);
        }

        return new RedirectResult(commandOptional.get(), query);
    }

    public String generateRedirectUrl(RedirectResult redirectResult) {
        String urlPattern = redirectResult.getCommand().getUrlPattern();
        String query = redirectResult.getQuery();

        // If the URL pattern doesn't contain {query} placeholder, return it as is
        if (!urlPattern.contains("{query}")) {
            return urlPattern;
        }

        // Otherwise, replace the placeholder with the encoded query
        try {
            String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8.toString());
            return urlPattern.replace("{query}", encodedQuery);
        } catch (UnsupportedEncodingException e) {
            // This should never happen with StandardCharsets.UTF_8
            return urlPattern.replace("{query}", query);
        }
    }

    public static class RedirectResult {
        private final Command command;
        private final String query;

        public RedirectResult(Command command, String query) {
            this.command = command;
            this.query = query;
        }

        public Command getCommand() {
            return command;
        }

        public String getQuery() {
            return query;
        }
    }
}

