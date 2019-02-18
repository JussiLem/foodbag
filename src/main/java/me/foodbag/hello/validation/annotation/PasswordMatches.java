package me.foodbag.hello.validation.annotation;

import me.foodbag.hello.validation.password.PasswordMatchesValidator;

import javax.validation.Constraint;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchesValidator.class)
@Documented
public @interface PasswordMatches {

  String message() default "Passwords don't match";
}
