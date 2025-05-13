package com.chat.userservice.exps;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@SuppressWarnings({"unchecked", "rawtypes"})
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        if (ex.getMessage() != null && (ex.getMessage().equals("Access is denied") || ex.getMessage().equals("Доступ запрещен")))
            return new ResponseEntity(errorDetails, HttpStatus.EXPECTATION_FAILED);
        else
            return new ResponseEntity(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RecordBadRequestException.class)
    public final ResponseEntity<Object> handleBadRequestException(RecordBadRequestException ex) {
        return new ResponseEntity(new ErrorDetails(new Date(), ex.getMessage(), "Something is wrong!"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SmsException.class)
    public final ResponseEntity<Object> handleBadRequestException(SmsException ex) {
        return new ResponseEntity(new ErrorDetails(new Date(), ex.getMessage(), "Sms part is wrong!"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public final ResponseEntity<Object> handleBadRequestException(RecordNotFoundException ex) {
        return new ResponseEntity(new ErrorDetails(new Date(), ex.getMessage(), "Something is wrong!"), HttpStatus.BAD_REQUEST);
    }
}