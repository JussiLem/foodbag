package me.foodbag.hello.validation.annotation;

import me.foodbag.hello.validation.password.PasswordMatchesValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/** Has to be applied in UserDto Object */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME) // Runtime annotation
@Constraint(validatedBy = PasswordMatchesValidator.class)
@Documented
public @interface PasswordMatches {

  String message() default "Passwords don't match";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
