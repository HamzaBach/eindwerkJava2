package com.example.eindwerkJava2.model.dto;

import com.example.eindwerkJava2.model.Role;
import com.example.eindwerkJava2.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class UserDto extends User {

    private String newPassword;
    private String confirmPassword;

    public void convertUserToDTO(User user){
        setUserId(user.getUserId());
        setUserName(user.getUserName());
        setUserImage(user.getUserImage());
        setRoles(user.getRoles());
    }
    public User convertDTOtoUser(){
        User user = new User();
        user.setUserId(this.getUserId());
        user.setUserName(this.getUserName());
        user.setPassword(this.getPassword());
        user.setActiveUser(this.getActiveUser());
        user.setUserImage(this.getUserImage());
        user.setRoles(this.getRoles());
        return user;
    }

}
