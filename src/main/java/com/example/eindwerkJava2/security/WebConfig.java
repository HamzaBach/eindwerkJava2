package com.example.eindwerkJava2.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * This configuration is used for setting up the login page for Spring Security.
 *
 * @author Hamza Bachiri
 * @version 1.0
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    /**
     * This method adds a viewcontroller for the login page.
     * WebsecurityConfig refers to it in the httpsecurity config method.
     * @see com.example.eindwerkJava2.security.WebSecurityConfig
     * @param registry The ViewControllerRegistry is used for setting up the dedicated loginpage.
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }
}
