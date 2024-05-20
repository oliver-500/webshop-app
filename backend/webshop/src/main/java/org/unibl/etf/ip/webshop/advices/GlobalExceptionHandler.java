package org.unibl.etf.ip.webshop.advices;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.unibl.etf.ip.webshop.exceptions.*;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleNotFound() {
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public void handleIncorrectPassword() {
    }

    @ExceptionHandler(IncorrectPINCodeException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public void handleIncorrectPINCode() {
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public void handleConflict() {
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public void handleUnauthorized() {
    }

    @ExceptionHandler(ActivationTimeoutException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleActivationTimeout() {
    }

    @ExceptionHandler(WaitingAuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public void handleWaitingAuthentication() {

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors.toString());
    }


}
