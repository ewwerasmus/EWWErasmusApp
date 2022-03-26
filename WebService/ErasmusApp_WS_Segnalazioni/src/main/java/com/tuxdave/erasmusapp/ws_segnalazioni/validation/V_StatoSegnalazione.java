package com.tuxdave.erasmusapp.ws_segnalazioni.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = ValidatorStatoSegnalazione.class)
public @interface V_StatoSegnalazione {
    String message() default "L'id di StatoSegnalazione deve essere compreso tra 0-2 in inserimento!";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
