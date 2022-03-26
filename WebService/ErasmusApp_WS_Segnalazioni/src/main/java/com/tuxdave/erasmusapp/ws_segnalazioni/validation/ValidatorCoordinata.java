package com.tuxdave.erasmusapp.ws_segnalazioni.validation;

import com.tuxdave.erasmusapp.ws_segnalazioni.entity.Coordinata;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidatorCoordinata implements ConstraintValidator<V_Coordinata, Coordinata> {
    @Override
    public boolean isValid(Coordinata coordinata, ConstraintValidatorContext constraintValidatorContext) {
        return coordinata.getId() == null;
    }
}
