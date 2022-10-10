package com.ead.authuser.validation;

import com.ead.authuser.validation.impl.CepConstraintImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.METHOD})
@Constraint(validatedBy = CepConstraintImpl.class)
public @interface CepConstraint {
    String message() default  "Invalid cep";
    Class<?> [] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
