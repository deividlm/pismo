package com.deividlm.pismo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class InvalidInputException extends RuntimeException{


    public InvalidInputException(String message){
        super(message);
    }
}
