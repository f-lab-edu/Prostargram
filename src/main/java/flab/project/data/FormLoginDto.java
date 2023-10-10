package flab.project.data;

import flab.project.data.enums.UserType;
import lombok.Getter;

@Getter
public class FormLoginDto {

    private long userId;
    private String email;
    private String password;
    private UserType userType;
}