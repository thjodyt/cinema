package com.thjodyt.cinema.data.validation;

import static com.thjodyt.cinema.data.validation.SignSolver.*;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        boolean containsSmallLetter = false;
        boolean containsBigLetter = false;
        boolean containsDigit = false;
        if (value == null) {
            return false;
        }
        for (int i = 0; i < value.length(); i++) {
            if (isSmallLetter(value.charAt(i))) {
                containsSmallLetter = true;
            } else if (isBigLetter(value.charAt(i))) {
                containsBigLetter = true;
            } else if (isDigit(value.charAt(i))) {
                containsDigit = true;
            }
        }
        return containsSmallLetter && containsBigLetter && containsDigit;
    }
}
