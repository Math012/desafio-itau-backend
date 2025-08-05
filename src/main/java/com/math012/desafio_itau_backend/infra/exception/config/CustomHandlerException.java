package com.math012.desafio_itau_backend.infra.exception.config;

import com.math012.desafio_itau_backend.infra.exception.RequestInvalidException;
import com.math012.desafio_itau_backend.infra.exception.TransactionDateException;
import com.math012.desafio_itau_backend.infra.exception.TransactionValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class CustomHandlerException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TransactionDateException.class)
    public ResponseEntity<StructException> handlerTransactionDateException(Exception e, WebRequest request){
        StructException exception = new StructException(new Date(),e.getMessage(),request.getDescription(false));
        return new ResponseEntity<>(exception, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(TransactionValueException.class)
    public ResponseEntity<StructException> handlerTransactionValue(Exception e, WebRequest request){
        StructException exception = new StructException(new Date(),e.getMessage(),request.getDescription(false));
        return new ResponseEntity<>(exception, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(RequestInvalidException.class)
    public ResponseEntity<StructException> handlerRequestInvalidException(Exception e, WebRequest request){
        StructException exception = new StructException(new Date(),e.getMessage(),request.getDescription(false));
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }
}