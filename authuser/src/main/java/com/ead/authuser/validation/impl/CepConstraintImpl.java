package com.ead.authuser.validation.impl;

import com.ead.authuser.repository.CepRepository;
import com.ead.authuser.validation.CepConstraint;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class CepConstraintImpl implements ConstraintValidator<CepConstraint,String> {


    public final CepRepository cepRepository;

    public CepConstraintImpl(CepRepository cepRepository) {
        this.cepRepository = cepRepository;
    }

    /**
     * @param constraintAnnotation
     */
    @Override
    public void initialize(CepConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    /**
     * @param cep
     * @param constraintValidatorContext
     * @return
     */
    @Override
    public boolean isValid(String cep, ConstraintValidatorContext constraintValidatorContext) {
        try {
            var cepOpt = cepRepository.cep(cep);
            boolean hasError = cepOpt.get().toString().contains("erro");
            if(hasError)
                return false;
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
