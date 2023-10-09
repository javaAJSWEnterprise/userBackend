package com.example.userbackend.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public class AgeValidator implements ConstraintValidator<AgeConstraint, Date> {

    private int minAge;

    @Override
    public void initialize(AgeConstraint constraint) {
        minAge = constraint.value();
    }

    @Override
    public boolean isValid(Date birthDate, ConstraintValidatorContext context) {
        if (birthDate == null) {
            return true;
        }

        LocalDate currentDate = LocalDate.now();
        LocalDate birthLocalDate = birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        int age = Period.between(birthLocalDate, currentDate).getYears();

        return age >= minAge;
    }
}

