package javatp.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javatp.domain.Message;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseEntity<Object> exception(AuthenticationException exception) {
        return ResponseEntity.badRequest().body(new Message("Authentication error", exception.getMessage()));
    }
}