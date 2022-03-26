package com.tuxdave.erasmusapp.ws_segnalazioni.validation;

import com.tuxdave.erasmusapp.ws_segnalazioni.entity.StatoSegnalazione;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidatorStatoSegnalazione implements ConstraintValidator<V_StatoSegnalazione, com.tuxdave.erasmusapp.ws_segnalazioni.entity.StatoSegnalazione> {
    @Override
    public boolean isValid(StatoSegnalazione value, ConstraintValidatorContext context) {
        return value.getId() >= 0 && value.getId() <= 2;
    }
}
