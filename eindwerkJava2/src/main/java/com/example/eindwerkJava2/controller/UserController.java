package com.example.eindwerkJava2.controller;

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

/**
 * The controller layer for connecting the front-end with the back-end for users.
 *
 * @author Hamza Bachiri
 * @version 1.0
 */

@Controller
@RequestMapping
public class UserController {
    /**
     * Autowiring of the UserService from the IOC container.
     */
    @Autowired
    private final UserService userService;
    /**
     * Autowiring of the RolesService from the IOC container.
     */
    @Autowired
    private final RolesService rolesService;

    /**
     * Dependency injection of the UserService & RolesService into the constructor of the UserController.
     *
     * @param userService
     * @param rolesService
     */
    public UserController(UserService userService, RolesService rolesService) {
        this.userService = userService;
        this.rolesService = rolesService;
    }

    /**
     * Endpoint (GET) that returns all the users.
     *
     * @param model Model is used for adding the UsersList as an attribute of the model to be accessed by the front-end.
     * @return Users html template is returned with an overview of all active users.
     */
    @GetMapping("/users")
    public String getUsers(Model model) {
        List<User> users = userService.getActiveUsers();
        model.addAttribute("UsersList", users);
        return "users";
    }

    /**
     * Endpoint (POST) that saves a new user.
     *
     * @param user          Object that is used for storing the new user in the persistence layer via the UserService.
     * @param multipartFile This is the picture of the user that is stored in the persistence layer via the UserService.
     * @return After a successful save the users overview is returned.
     * @throws IOException Exception is triggered in case the save action failed.
     */
    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") User user,
                           @RequestParam("image") MultipartFile multipartFile) throws IOException {
        byte[] addedImage = multipartFile.getBytes();
        this.userService.saveUser(user, addedImage);
        return "redirect:/users";
    }

    /**
     * Endpoint (GET) that returns the image of the user.
     *
     * @param userId   The id of the user for retrieving it from the persistence layer.
     * @param response It is used for preparing the image to be used in the front-end.
     * @param user     The user object that is used for obtaining the user's image.
     * @throws IOException Exception is triggered in case the retrieval of the image goes wrong.
     */
    @GetMapping("/user/image/{userId}")
    @ResponseBody
    void showImage(@PathVariable("userId") Long userId, HttpServletResponse response, Optional<User> user)
            throws IOException {
        if (user.get().getUserImage() != null) {
            user = userService.findById(userId);
            response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
            response.getOutputStream().write(user.get().getUserImage());
            response.getOutputStream().close();
        }
    }

    /**
     * Endpoint (GET) for editing a user's settings and roles.
     *
     * @param userId The id of the user for retrieving it from the persistence layer.
     * @param model  Model is used for adding the Roles, UserRoles and UserNotRoles as an attribute of the model to be accessed by the front-end.
     * @return The form containing the settings of the user is returned.
     */
    @GetMapping("edit/user/{userId}")
    public String showEditUserForm(@PathVariable("userId") Long userId, Model model) {
        User user = userService.findById(userId).get();
        model.addAttribute("rolesList", rolesService.getAllRoles());
        model.addAttribute("userRoles", rolesService.getUserRoles(user));
        model.addAttribute("userNotRoles", rolesService.getUserNotRoles(user));
        model.addAttribute("user", user);
        return "/forms/form_user";
    }

    /**
     * Endpoint (GET) for deleting an user from the persistence layer.
     *
     * @param userId The id of the user for retrieving it from the persistence layer.
     * @return After a successful delete the users overview is returned.
     */
    @GetMapping("delete/user/{userId}")
    public String deleteUser(@PathVariable("userId") Long userId) {
        User user = userService.findById(userId).get();
        this.userService.deleteUser(user);
        return "redirect:/users";
    }

    /**
     * Endpoint (GET) for adding a new user to the persistence layer.
     *
     * @param model Model is used for adding the host user object and a list of all available roles to be used in the front-end.
     * @return The form for adding a new user is returned.
     */
    @GetMapping("/new/user")
    public String showNewUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("rolesList", rolesService.getAllRoles());
        return "/forms/form_user";
    }
}
