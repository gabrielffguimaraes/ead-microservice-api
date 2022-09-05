package com.ead.authuser.validation.impl;

import com.ead.authuser.validation.UsernameConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameConstraintImpl implements ConstraintValidator<UsernameConstraint, String> {
    /**
     * @param constraintAnnotation
     */
    @Override
    public void initialize(UsernameConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    /**
     * @param username
     * @param constraintValidatorContext
     * @return
     */
    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        if(username == null || username.trim().isEmpty() || username.contains(" ")) {
            return false;
        }
        return true;
    }
}
