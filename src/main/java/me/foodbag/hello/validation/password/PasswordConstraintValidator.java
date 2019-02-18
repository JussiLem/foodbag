package me.foodbag.hello.validation.password;

import com.google.common.base.Joiner;
import me.foodbag.hello.validation.annotation.ValidPassword;
import org.passay.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

  @Override
  public void initialize(final ValidPassword constraintAnnotation) {}

  @Override
  public boolean isValid(final String password, ConstraintValidatorContext validatorContext) {
    final PasswordValidator validator =
        new PasswordValidator(
            Arrays.asList(
                new LengthRule(8, 30),
                new UsernameRule(),
                new RepeatCharacterRegexRule(),
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                new CharacterRule(EnglishCharacterData.Digit, 1),
                new CharacterRule(EnglishCharacterData.Special, 1),
                new WhitespaceRule()));
    final RuleResult result = validator.validate(new PasswordData(password));
    if (result.isValid()) {
      return true;
    }
    validatorContext.disableDefaultConstraintViolation();
    validatorContext
        .buildConstraintViolationWithTemplate(Joiner.on(",").join(validator.getMessages(result)))
        .addConstraintViolation();
    return true;
  }
}
