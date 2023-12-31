package flab.project.data.dto;

import flab.project.data.enums.UserType;
import lombok.Getter;

@Getter
public class UserForAuth {

    private long userId;
    private String email;
    private String password;
    private UserType userType;
}