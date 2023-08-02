package com.pragma.usuario.micro.infrastructure.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ControllerAdvice
public class ControllerAdvisorJpa {
    private static final String ERROR_CODE = "error code";
    private static final String ERROR_DESCRIPTION = "error description";
    private static final String ERROR_MESSAGE = "message";

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex){
        Pattern pattern = Pattern.compile("Duplicate entry '(.+?)' for key");
        Matcher matcher = pattern.matcher(ex.getMessage());
        String mensaje = "Data integrity error, ";
        if(matcher.find()) {
            String entradaDuplicada = matcher.group(1);
            mensaje += "'" + entradaDuplicada + "'" + " already exists";
        }
        else {
            mensaje += ex.getMessage();
        }
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(ERROR_CODE, HttpStatus.CONFLICT.value());
        body.put(ERROR_DESCRIPTION, HttpStatus.CONFLICT);
        body.put(ERROR_MESSAGE, mensaje);
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> businessException(BusinessException ex){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(ERROR_CODE, HttpStatus.valueOf(ex.getCode()));
        body.put(ERROR_DESCRIPTION, HttpStatus.valueOf(ex.getCode()));
        body.put(ERROR_MESSAGE, ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.valueOf(ex.getCode()));
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> customException(CustomException ex){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(ERROR_CODE, ex.getStatus());
        body.put(ERROR_DESCRIPTION, ex.getStatus());
        body.put(ERROR_MESSAGE, ex.getMessage());
        return new ResponseEntity<>(body, ex.getStatus());
    }

}
