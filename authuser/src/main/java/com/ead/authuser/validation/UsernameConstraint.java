package com.ead.authuser.validation;

import com.ead.authuser.validation.impl.UsernameConstraintImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UsernameConstraintImpl.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.FIELD})
public @interface UsernameConstraint {
    String message() default  "Invalid username";
    Class<?> [] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
