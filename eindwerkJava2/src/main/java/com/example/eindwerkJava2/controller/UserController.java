package com.example.eindwerkJava2.controller;

import com.example.eindwerkJava2.model.Article;
import com.example.eindwerkJava2.model.EmployeeRole;
import com.example.eindwerkJava2.model.User;
import com.example.eindwerkJava2.service.RolesService;
import com.example.eindwerkJava2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping
public class UserController {
    @Autowired
    private final UserService userService;
    @Autowired
    private final RolesService rolesService;

    public UserController(UserService userService, RolesService rolesService){
        this.userService=userService;
        this.rolesService=rolesService;
    }

    @GetMapping("/users")
    public String getUsers(Model model){
        List<User> users = userService.getActiveUsers();
        model.addAttribute("UsersList",users);
        return "users";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") User user,
                              @RequestParam("image") MultipartFile multipartFile) throws IOException {
        byte[] addedImage = multipartFile.getBytes();
        this.userService.saveUser(user,addedImage);
        return "redirect:/users";
    }

    @GetMapping("/user/image/{userId}")
    @ResponseBody
    void showImage(@PathVariable("userId") Long userId, HttpServletResponse response, Optional<User> user)
            throws IOException {
        if(user.get().getUserImage()!=null){
            user = userService.findById(userId);
            response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
            response.getOutputStream().write(user.get().getUserImage());
            response.getOutputStream().close();
        }
    }

    @GetMapping("edit/user/{userId}")
    public String showEditUserForm(@PathVariable("userId") Long userId, Model model) {
        User user = userService.findById(userId).get();
        model.addAttribute("rolesList", rolesService.getAllRoles());
        model.addAttribute("userRoles", rolesService.getUserRoles(user));
        model.addAttribute("userNotRoles", rolesService.getUserNotRoles(user));
        model.addAttribute("user", user);
        return "/forms/form_user";
    }


    @GetMapping("delete/user/{userId}")
    public String deleteUser(@PathVariable("userId") Long userId){
        User user = userService.findById(userId).get();
        this.userService.deleteUser(user);
        return "redirect:/users";
    }

    @GetMapping("/new/user")
    public String showNewUserForm(Model model) {
        model.addAttribute("user",new User());
        model.addAttribute("rolesList", rolesService.getAllRoles());
        return "/forms/form_user";
    }


}
