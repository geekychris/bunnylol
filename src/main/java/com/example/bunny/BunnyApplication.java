package com.example.bunny;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for the Bunny URL redirection service.
 * This application provides a redirecting framework similar to bunnylol,
 * allowing users to enter shorthand commands in their browser's search bar
 * to be redirected to specific websites.
 */
@SpringBootApplication
public class BunnyApplication {

    public static void main(String[] args) {
        SpringApplication.run(BunnyApplication.class, args);
    }
}

