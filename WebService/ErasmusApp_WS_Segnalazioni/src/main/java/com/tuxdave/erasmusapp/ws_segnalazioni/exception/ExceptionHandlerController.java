package com.tuxdave.erasmusapp.ws_segnalazioni.exception;

import com.tuxdave.erasmusapp.shared.exception.ErrorMsg;
import com.tuxdave.erasmusapp.shared.exception.classic.SaveException;
import com.tuxdave.erasmusapp.shared.exception.custom.BindingException;
import com.tuxdave.erasmusapp.shared.exception.custom.DuplicateException;
import com.tuxdave.erasmusapp.shared.exception.custom.NotFoundException;
import com.tuxdave.erasmusapp.shared.exception.custom.UnauthoruzedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RestController
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {
    //put here the beans to catch the exceptions

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<ErrorMsg> notFoundExceptionHandler(NotFoundException ex){
        ErrorMsg msg = new ErrorMsg(
                new Date(),
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage()
        );
        return new ResponseEntity<ErrorMsg>(msg, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String msgErr = ex.getMessage();
        if(msgErr.contains("JSON")){
            msgErr = "È presente un errore nella sintassi del JsonArray fornito per l'inserimento!";
        }
        ErrorMsg msg = new ErrorMsg(
                new Date(),
                HttpStatus.BAD_REQUEST.value(),
                msgErr
        );
        return new ResponseEntity<Object>(msg, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {BindingException.class})
    public final ResponseEntity<ErrorMsg> bindingExceptionHandler(Exception ex){
        ErrorMsg msg = new ErrorMsg(
          new Date(),
          HttpStatus.BAD_REQUEST.value(),
          ex.getMessage()
        );
        return new ResponseEntity<ErrorMsg>(msg, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateException.class)
    public final ResponseEntity<ErrorMsg> duplicateExceptionHandler(DuplicateException ex) {
        ErrorMsg msg = new ErrorMsg(
                new Date(),
                HttpStatus.CONFLICT.value(),
                ex.getMessage()
        );
        return new ResponseEntity<ErrorMsg>(msg, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(SaveException.class)
    public final ResponseEntity<ErrorMsg> duplicateExceptionHandler(SaveException ex) {
        ErrorMsg msg = new ErrorMsg(
                new Date(),
                HttpStatus.NOT_ACCEPTABLE.value(),
                ex.getMessage()
        );
        return new ResponseEntity<ErrorMsg>(msg, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(UnauthoruzedException.class)
    public final ResponseEntity<ErrorMsg> unauthorizedExceptionHandler(UnauthoruzedException ex){
        ErrorMsg msg = new ErrorMsg(
                new Date(),
                HttpStatus.UNAUTHORIZED.value(),
                ex.getMessage()
        );
        return new ResponseEntity<ErrorMsg>(msg, HttpStatus.UNAUTHORIZED);
    }
}
