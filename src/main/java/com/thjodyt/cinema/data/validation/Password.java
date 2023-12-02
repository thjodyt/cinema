package com.thjodyt.cinema.data.validation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
@Documented
public @interface Password {

    String message() default "Hasło musi zawierać 8 znaków w tym dużą i małą angielską literę oraz cyfrę.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
