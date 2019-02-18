package me.foodbag.hello.validation.annotation;

import me.foodbag.hello.validation.EmailValidator;

import javax.validation.Constraint;
import java.lang.annotation.*;

@Target( {ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
@Documented
public @interface ValidEmail {

    String message() default "Invalid Email";
}
