package com.example.eindwerkJava2.controller;

import com.example.eindwerkJava2.model.Article;
import com.example.eindwerkJava2.model.User;
import com.example.eindwerkJava2.service.RolesService;
import com.example.eindwerkJava2.service.UserService;
import com.example.eindwerkJava2.wrappers.CategorySuccess;
import com.example.eindwerkJava2.wrappers.RolesSuccess;
import com.example.eindwerkJava2.wrappers.SuccessObject;
import com.example.eindwerkJava2.wrappers.UserSuccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        UserSuccess retrievedUsers = userService.getActiveUsers();
        if (!retrievedUsers.getIsSuccessfull()) {
            model.addAttribute("error", retrievedUsers.getMessage());
        } else {
            List<User> users = retrievedUsers.getUsers();
            model.addAttribute("UsersList", users);
        }
        return "users";
    }

    /**
     * Endpoint (POST) that saves a new user.
     *
     * @param user          Object that is used for storing the new user in the persistence layer via the UserService.
     * @param multipartFile This is the picture of the user that is stored in the persistence layer via the UserService.
     * @param redirAttrs    The redirect attribute is used for the error/success message handling.
     * @return After a successful save the users overview is returned.
     * @throws IOException Exception is triggered in case the save action failed.
     */
    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") User user,
                           @RequestParam("image") MultipartFile multipartFile, RedirectAttributes redirAttrs, Model model) throws IOException {

        byte[] addedImage = multipartFile.getBytes();
        SuccessObject savedUser = this.userService.saveUser(user, addedImage);
        if (savedUser.getIsSuccessfull()) {
            redirAttrs.addFlashAttribute("success", savedUser.getMessage());
            return "redirect:/users";
        } else {
            model.addAttribute("error", savedUser.getMessage());
            return "/forms/form_user";
        }
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
    void showImage(@PathVariable("userId") Long userId, HttpServletResponse response, User user)
            throws IOException {
        if (user.getUserImage() != null) {
            UserSuccess success = userService.findById(userId);
            if (success.getIsSuccessfull()) {
                user = success.getUser();
                response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
                response.getOutputStream().write(user.getUserImage());
                response.getOutputStream().close();
            }

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
        UserSuccess success = userService.findById(userId);
        if (success.getIsSuccessfull()) {
            User user = success.getUser();
            model.addAttribute("rolesList", rolesService.getAllRoles());
            model.addAttribute("userRoles", rolesService.getUserRoles(user));
            model.addAttribute("userNotRoles", rolesService.getUserNotRoles(user));
            model.addAttribute("user", user);
        } else {
            model.addAttribute("error", success.getMessage());
        }
        return "/forms/form_user";
    }

    /**
     * Endpoint (GET) for deleting an user from the persistence layer.
     *
     * @param userId The id of the user for retrieving it from the persistence layer.
     * @return After a successful delete the users overview is returned.
     */
    @GetMapping("delete/user/{userId}")
    public String deleteUser(@PathVariable("userId") Long userId, RedirectAttributes redirAttrs) {
        UserSuccess findUser = userService.findById(userId);
        if (findUser.getIsSuccessfull()) {
            SuccessObject toBeDeletedUser = this.userService.deleteUser(findUser.getUser());
            if (toBeDeletedUser.getIsSuccessfull()) {
                redirAttrs.addFlashAttribute("success", toBeDeletedUser.getMessage());
            } else {
                redirAttrs.addFlashAttribute("error", toBeDeletedUser.getMessage());
            }
        } else {
            redirAttrs.addFlashAttribute("error", findUser.getMessage());
        }
        return "redirect:/users";
    }

    /**
     * Endpoint (GET) for adding a new user to the persistence layer.
     *
     * @param model Model is used for adding the host user object and a list of all available roles to be used in the front-end.
     * @return The form for adding a new user is returned.
     */
    @GetMapping("/new/user")
    public String showNewUserForm(Model model, RedirectAttributes redirAttrs) {
        RolesSuccess rolesSuccess = rolesService.getAllRoles();
        if (rolesSuccess.getIsSuccessfull()) {
            model.addAttribute("user", new User());
            model.addAttribute("rolesList", rolesSuccess.getRoles());
            return "/forms/form_user";
        } else {
            redirAttrs.addAttribute("error", rolesSuccess.getMessage());
            return "redirect:/users";
        }
    }
}
