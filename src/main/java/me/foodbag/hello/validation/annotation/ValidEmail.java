package me.foodbag.hello.validation.annotation;

import me.foodbag.hello.validation.EmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Custom validator for validating email address, instead of using Hiberante @Email. Hibernate
 * validates old intranet addresses format: address@server as valid, which is no good. More from
 * this in stackoverflow <a
 * href="https://stackoverflow.com/questions/4459474/hibernate-validator-email-accepts-askstackoverflow-as-valid"</a>
 */
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
@Documented
public @interface ValidEmail {

  String message() default "Invalid Email";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
