package com.app.api.controller;


import java.util.HashMap;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandleExceptionAdviserController extends Controller{


    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<?> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> errors = e.getBindingResult().getFieldErrors();
        HashMap<String, Object> errorHashMap = new HashMap<>();
        errors.forEach(error -> errorHashMap.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(message.error("402", errorHashMap));

    }

}
