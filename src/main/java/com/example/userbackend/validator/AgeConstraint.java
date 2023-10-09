package com.example.userbackend.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AgeValidator.class)
@Documented
public @interface AgeConstraint {
    String message() default "User must be at least 16 years old";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    int value();
}