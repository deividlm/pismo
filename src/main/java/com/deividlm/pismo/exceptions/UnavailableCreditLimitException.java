package com.deividlm.pismo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UnavailableCreditLimitException extends RuntimeException {

    public UnavailableCreditLimitException(String message) {
        super(message);
    }
}
