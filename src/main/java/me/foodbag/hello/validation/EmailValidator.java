package me.foodbag.hello.validation;

import me.foodbag.hello.validation.annotation.ValidEmail;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validates the email with the EMAIL_PATTERN regex pattern.
 */
public class EmailValidator implements ConstraintValidator<ValidEmail, String> {
  private static final String EMAIL_PATTERN =
      "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

  @Override
  public void initialize(final ValidEmail constraintAnnotation) {}

  @Override
  public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
    return (validateEmail(username));
  }

  private boolean validateEmail(final String email) {
    Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    Matcher matcher = pattern.matcher(email);
    return matcher.matches();
  }
}
