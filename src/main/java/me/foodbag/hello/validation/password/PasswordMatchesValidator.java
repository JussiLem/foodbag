package me.foodbag.hello.validation.password;

import me.foodbag.hello.validation.annotation.PasswordMatches;
import me.foodbag.hello.web.dto.UserDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
        //
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        final UserDto user = (UserDto) o;
        return user.getPassword().equals(user.getMatchingPassword());
    }
}
