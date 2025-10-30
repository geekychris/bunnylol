package com.example.bunny.controller;

import com.example.bunny.model.Command;
import com.example.bunny.service.RedirectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Map;
import java.util.Optional;

@Controller
public class RedirectController {

    private final RedirectService redirectService;

    @Autowired
    public RedirectController(RedirectService redirectService) {
        this.redirectService = redirectService;
    }

    /**
     * Home page that displays all available commands and usage instructions
     */
    @GetMapping("/")
    public String home(Model model) {
        Map<String, Command> commands = redirectService.getAllCommands();
        model.addAttribute("commands", commands);
        return "home";
    }

    /**
     * Main redirect endpoint that handles the command parsing and redirection
     * Example: /r?q=yt+cats (redirects to YouTube search for "cats")
     */
    @GetMapping("/r")
    public RedirectView redirect(@RequestParam(value = "q", required = false) String query) {
        if (query == null || query.trim().isEmpty()) {
            return new RedirectView("/");
        }
        
        RedirectService.RedirectResult result = redirectService.parseInput(query);
        String redirectUrl = redirectService.generateRedirectUrl(result);
        
        return new RedirectView(redirectUrl);
    }
    
    /**
     * Alternative endpoint that accepts the command directly in the path
     * Example: /yt/cats (redirects to YouTube search for "cats")
     */
    @GetMapping("/{command}")
    public RedirectView directCommand(
            @PathVariable("command") String command,
            @RequestParam(value = "q", required = false) String query) {
        
        String input = command;
        if (query != null && !query.trim().isEmpty()) {
            input += " " + query;
        }
        
        RedirectService.RedirectResult result = redirectService.parseInput(input);
        String redirectUrl = redirectService.generateRedirectUrl(result);
        
        return new RedirectView(redirectUrl);
    }
    
    /**
     * Fallback for direct access with a command and path segment
     * Example: /yt/search/cats (redirects to YouTube search for "search cats")
     */
    @GetMapping("/{command}/**")
    public RedirectView pathCommand(@PathVariable("command") String command, 
                                  @RequestParam(value = "q", required = false) String queryParam) {
        // Get the remaining path after the command
        String remainingPath = Optional.ofNullable(
                        ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                        .getRequest().getAttribute(
                                HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE))
                .map(Object::toString)
                .orElse("");
        
        // Extract the part after /{command}/
        String pathQuery = "";
        if (remainingPath.length() > command.length() + 2) {
            pathQuery = remainingPath.substring(command.length() + 2);
        }
        
        // Combine path and query parameter if both exist
        String query = pathQuery;
        if (queryParam != null && !queryParam.isEmpty()) {
            query = pathQuery.isEmpty() ? queryParam : pathQuery + " " + queryParam;
        }
        
        // Process the command + query
        String input = command;
        if (!query.isEmpty()) {
            input += " " + query;
        }
        
        RedirectService.RedirectResult result = redirectService.parseInput(input);
        String redirectUrl = redirectService.generateRedirectUrl(result);
        
        return new RedirectView(redirectUrl);
    }
}

