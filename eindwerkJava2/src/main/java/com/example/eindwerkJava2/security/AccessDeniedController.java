package com.example.eindwerkJava2.security;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Dedicated controller for handling access-denied places within the application for users with insufficient roles.
 *
 * @author Hamza Bachiri
 * @version 1.0
 */
@Controller
public class AccessDeniedController {

    /**
     * Endpoint (GET) to display the access-denied page in the front-end.
     * @return The access-denied page.
     */
    @GetMapping("/access-denied")
    public String getAccessDenied() {
        return "/error/accessDenied";
    }
}
