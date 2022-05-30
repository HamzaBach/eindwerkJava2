package com.example.eindwerkJava2.controller.restcontroller;

import com.example.eindwerkJava2.model.Location;
import com.example.eindwerkJava2.model.User;
import com.example.eindwerkJava2.service.UserService;
import com.example.eindwerkJava2.wrappers.SuccessEvaluator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getUsers() throws JSONException {
        List<User> activeUsers = userService.getActiveUsers().getEntities();

        JSONArray ja = new JSONArray();

        for (User user : activeUsers) {
            JSONObject jo = new JSONObject();
            jo.put("id", user.getUserId());
            jo.put("userName", user.getUserName());
            ja.put(jo);
        }
        JSONObject json = new JSONObject();
        json.put("users", ja);
        return json.toString();
    }
}
