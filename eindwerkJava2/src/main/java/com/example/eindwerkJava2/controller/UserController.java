package com.example.eindwerkJava2.controller;

import com.example.eindwerkJava2.model.User;
import com.example.eindwerkJava2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService){
        this.userService=userService;
    }

    @GetMapping("/users")
    public String getUsers(Model model){
        List<User> users = userService.getUsers();
        model.addAttribute("UsersList",users);
        return "users";
    }

/*    @GetMapping("/showNewUserForm")
    public String showNewUserForm(Model model){
        User user=new User();

    }*/

}
