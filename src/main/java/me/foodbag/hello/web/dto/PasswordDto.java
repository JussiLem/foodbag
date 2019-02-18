package me.foodbag.hello.web.dto;

import lombok.Getter;
import lombok.Setter;
import me.foodbag.hello.validation.annotation.ValidPassword;

@Getter
@Setter
public class PasswordDto {

  private String oldPassword;

  @ValidPassword private String newPassword;
}
