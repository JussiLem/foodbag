package me.foodbag.hello.security;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordValid {

    private String oldPassword;

    private String newPassword;
}
