package com.deividlm.pismo.exceptions;

import java.io.Serializable;
import java.util.Date;

public class ExceptionResponseEntity implements Serializable {



    private String message;
    private String exceptionDetails;
    private Date date;

    public ExceptionResponseEntity(String message, String exceptionDetails, Date date){
        super();
        this.message = message;
        this.exceptionDetails = exceptionDetails;
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getExceptionDetails() {
        return exceptionDetails;
    }

    public void setExceptionDetails(String exceptionDetails) {
        this.exceptionDetails = exceptionDetails;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
