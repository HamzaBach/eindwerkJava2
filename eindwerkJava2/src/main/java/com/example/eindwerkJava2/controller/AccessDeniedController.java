package com.example.eindwerkJava2.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccessDeniedController {

    @GetMapping("/accessDenied")
    public String getAccessDenied() {
        return "accessDenied";
    }
}
