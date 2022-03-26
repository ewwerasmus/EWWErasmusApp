package com.tuxdave.erasmusapp.ws_segnalazioni.validation;

import com.tuxdave.erasmusapp.ws_segnalazioni.entity.Categoria;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidatorCategoria implements ConstraintValidator<V_Categoria, Categoria> {
    @Override
    public boolean isValid(Categoria categoria, ConstraintValidatorContext constraintValidatorContext) {
        return categoria.getId() != null;
    }
}
