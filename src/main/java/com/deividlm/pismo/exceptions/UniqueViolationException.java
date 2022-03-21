package com.deividlm.pismo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UniqueViolationException extends RuntimeException{


    public UniqueViolationException(String message){
        super(message);
    }
}
