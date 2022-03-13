package com.tuxdave.erasmusapp.ws_segnalazioni.exception;

import com.tuxdave.erasmusapp.ws_segnalazioni.exception.custom.BindingException;
import com.tuxdave.erasmusapp.ws_segnalazioni.exception.custom.NotFoundException;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.naming.Binding;
import java.util.Date;

@ControllerAdvice
@RestController
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {
    //put here the beans to catch the exceptions

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMsg> notFoundExceptionHandler(NotFoundException ex){
        ErrorMsg msg = new ErrorMsg(
                new Date(),
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage()
        );
        return new ResponseEntity<ErrorMsg>(msg, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BindingException.class)
    public ResponseEntity<ErrorMsg> bindingExceptionHandler(BindingException ex){
        ErrorMsg msg = new ErrorMsg(
          new Date(),
          HttpStatus.NOT_ACCEPTABLE.value(),
          ex.getMessage()
        );
        return new ResponseEntity<ErrorMsg>(msg, HttpStatus.NOT_ACCEPTABLE);
    }
}
