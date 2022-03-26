package com.tuxdave.erasmusapp.ws_segnalazioni.validation;

import com.tuxdave.erasmusapp.ws_segnalazioni.entity.Comune;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidationComune implements ConstraintValidator<V_Comune, Comune> {
    @Override
    public boolean isValid(Comune comune, ConstraintValidatorContext constraintValidatorContext) {
        return comune.getCodiceCatastale() != null;
    }
}
