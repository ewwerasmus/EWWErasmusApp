package com.tuxdave.erasmusapp.ws_segnalazioni.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = ValidatorCategoria.class)
public @interface V_Categoria {
    String message() default "L'id di Categoria non deve essere NULL in inserimento!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
