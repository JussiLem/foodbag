package me.foodbag.hello.web.dto;

import lombok.Data;
import me.foodbag.hello.validation.annotation.PasswordMatches;
import me.foodbag.hello.validation.annotation.ValidEmail;
import me.foodbag.hello.validation.annotation.ValidPassword;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@PasswordMatches
public class UserDto {

  @NotNull
  @Size(min = 1, message = "{Size.userDto.firstName}")
  private String firstName;

  @NotNull
  @Size(min = 1, message = "{Size.userDto.lastName}")
  private String lastName;

  @ValidPassword private String password;

  @NotNull
  @Size(min = 1)
  private String matchingPassword;

  @ValidEmail
  @NotNull
  @Size(min = 1, message = "{Size.userDto.email}")
  private String email;
}
