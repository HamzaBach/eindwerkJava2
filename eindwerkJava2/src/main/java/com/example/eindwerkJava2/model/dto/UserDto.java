package com.example.eindwerkJava2.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private String userName;
    private String password;
    private String confirmPassword;
    private String newPassword;

}
