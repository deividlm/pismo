package com.deividlm.pismo.handlers;

import com.deividlm.pismo.exceptions.ExceptionResponseEntity;
import com.deividlm.pismo.exceptions.InvalidAmountValueException;
import com.deividlm.pismo.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RestController
public class CustomExceptionsHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<ExceptionResponseEntity> handlerNotFoundException(Exception exception, WebRequest webRequest){
        ExceptionResponseEntity exceptionResponseEntity = new ExceptionResponseEntity(exception.getMessage(), webRequest.getDescription(false), new Date());
        return new ResponseEntity<>(exceptionResponseEntity, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidAmountValueException.class)
    public final ResponseEntity<ExceptionResponseEntity> handlerInvalidAmountValue(Exception exception, WebRequest webRequest){
        ExceptionResponseEntity exceptionResponseEntity = new ExceptionResponseEntity(exception.getMessage(), webRequest.getDescription(false), new Date());
        return new ResponseEntity<>(exceptionResponseEntity, HttpStatus.BAD_REQUEST);
    }


}
