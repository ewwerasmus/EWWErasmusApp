package com.tuxdave.erasmusapp.ws_segnalazioni.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = ValidatorCoordinata.class)
public @interface V_Coordinata {
    String message() default "L'id di Coordinata deve essere NULL in inserimento!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
