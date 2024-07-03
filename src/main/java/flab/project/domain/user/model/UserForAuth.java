package flab.project.domain.user.model;

import flab.project.domain.user.enums.UserType;
import lombok.Getter;

@Getter
public class UserForAuth {

    private long userId;
    private String email;
    private String password;
    private UserType userType;
}