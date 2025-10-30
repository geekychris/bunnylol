package com.example.bunny.model;

import java.util.Objects;

/**
 * Model class representing a redirect command.
 * Each command has a shortcode that users type, a target URL pattern, and a description.
 */
public class Command {
    private String shortcode;
    private String targetUrlPattern;
    private String description;

    /**
     * Default constructor
     */
    public Command() {
    }

    /**
     * Parameterized constructor
     * 
     * @param shortcode The command's shortcode (e.g., "yt" for YouTube)
     * @param targetUrlPattern The URL pattern to redirect to, with placeholders for query parameters
     * @param description A description of what the command does
     */
    public Command(String shortcode, String targetUrlPattern, String description) {
        this.shortcode = shortcode;
        this.targetUrlPattern = targetUrlPattern;
        this.description = description;
    }

    /**
     * Gets the command's shortcode
     * 
     * @return The shortcode
     */
    public String getShortcode() {
        return shortcode;
    }

    /**
     * Sets the command's shortcode
     * 
     * @param shortcode The shortcode to set
     */
    public void setShortcode(String shortcode) {
        this.shortcode = shortcode;
    }

    /**
     * Gets the target URL pattern
     * 
     * @return The target URL pattern
     */
    public String getTargetUrlPattern() {
        return targetUrlPattern;
    }
    
    /**
     * Alias for getTargetUrlPattern() for compatibility
     * 
     * @return The URL pattern
     */
    public String getUrlPattern() {
        return targetUrlPattern;
    }

    /**
     * Sets the target URL pattern
     * 
     * @param targetUrlPattern The target URL pattern to set
     */
    public void setTargetUrlPattern(String targetUrlPattern) {
        this.targetUrlPattern = targetUrlPattern;
    }

    /**
     * Gets the command description
     * 
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the command description
     * 
     * @param description The description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Command{" +
                "shortcode='" + shortcode + '\'' +
                ", targetUrlPattern='" + targetUrlPattern + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Command command = (Command) o;
        return Objects.equals(shortcode, command.shortcode) &&
                Objects.equals(targetUrlPattern, command.targetUrlPattern) &&
                Objects.equals(description, command.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shortcode, targetUrlPattern, description);
    }
}

