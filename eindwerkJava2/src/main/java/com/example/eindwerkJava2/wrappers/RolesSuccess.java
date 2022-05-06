package com.example.eindwerkJava2.wrappers;

import com.example.eindwerkJava2.model.Role;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class RolesSuccess extends SuccessObject{
    private Role role;
    private List<Role> roles;
}
