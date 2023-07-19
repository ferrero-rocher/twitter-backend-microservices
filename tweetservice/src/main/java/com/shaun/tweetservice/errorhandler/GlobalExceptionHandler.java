package com.shaun.tweetservice.errorhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> handleInValidException(MethodArgumentNotValidException exception)
    {


        List<String> errorMessages =exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error->error.getDefaultMessage())
                .collect(Collectors.toList());


        Error errorpayload = new Error("400",errorMessages.toString());
        return new ResponseEntity<>(errorpayload, HttpStatus.BAD_REQUEST);
    }

   /* @ExceptionHandler( ConstraintViolationException.class)
    public ResponseEntity<Error> handleViolationException(ConstraintViolationException exception)
    {
        List<String> errorMessages = exception.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());
        Error errorpayload = new Error("400",errorMessages.toString());
        return new ResponseEntity<>(errorpayload, HttpStatus.BAD_REQUEST);

    }*/
    @ExceptionHandler(NoRecordFoundException.class)
    public ResponseEntity<?> handleNoRecordException(NoRecordFoundException exception)
    {
        Error errorpayload = new Error("404", exception.getMessage());
        return new ResponseEntity<>(errorpayload, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(RecordAlreadyExistsException.class)
    public ResponseEntity<?> handleDuplicateRecordException(RecordAlreadyExistsException exception)
    {
        Error errorpayload = new Error("400", exception.getMessage());
        return new ResponseEntity<>(errorpayload, HttpStatus.BAD_REQUEST);

    }

}

