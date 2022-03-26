package com.tuxdave.erasmusapp.ws_segnalazioni.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = ValidationComune.class)
public @interface V_Comune {
    String message() default "Il codice catastale di Comune non deve essere NULL in inserimento!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
