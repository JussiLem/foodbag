package me.foodbag.hello.web.dto;

import lombok.Data;
import me.foodbag.hello.validation.annotation.PasswordMatches;
import me.foodbag.hello.validation.annotation.ValidEmail;
import me.foodbag.hello.validation.annotation.ValidPassword;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO / Data Transfer Object sends all the registration information to Spring backend.
 * The <b>DTO</b> should have all the information we'll require later on when we create
 * and populate <i>User</i> object. Using standard javax.validation annotations as well as own
 * custom validation annotations to validate the format of the email address as well as for the
 * password confirmation.
 */
@Data
@PasswordMatches
public class UserDto {

  @NotNull
  @Size(min = 1, message = "{Size.userDto.firstName}")
  private String firstName;

  @NotNull
  @Size(min = 1, message = "{Size.userDto.lastName}")
  private String lastName;

  /**
   * Checks that the password is valid by using custom annotation and validator
   */
  @ValidPassword private String password;

  @NotNull
  @Size(min = 1)
  private String matchingPassword;

  /**
   * Checks that the email is valid by using custom annotation and validator
   */
  @ValidEmail
  @NotNull
  @Size(min = 1, message = "{Size.userDto.email}")
  private String email;
}
