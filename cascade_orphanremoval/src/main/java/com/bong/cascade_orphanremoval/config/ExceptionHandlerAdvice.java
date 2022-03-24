package com.bong.cascade_orphanremoval.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerAdvice {

//    @ExceptionHandler({DataIntegrityViolationException.class})
//    public final ResponseEntity<String> handleException(DataIntegrityViolationException ex, WebRequest request) {
//        return ResponseEntity.ok("fail");
//    }

    @ExceptionHandler({DataAccessException.class})
    public final ResponseEntity<String> handleException(DataAccessException ex, WebRequest request) {
        return ResponseEntity.ok("fail");
    }

//    @ExceptionHandler({ConstraintViolationException.class})
//    public final ResponseEntity<String> handleException(ConstraintViolationException ex, WebRequest request) {
//        return ResponseEntity.ok("fail");
//    }

}
