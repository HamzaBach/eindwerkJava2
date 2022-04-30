package com.example.eindwerkJava2.wrappers;

import com.example.eindwerkJava2.model.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class UserSuccess extends SuccessObject{
    private User user;
}
