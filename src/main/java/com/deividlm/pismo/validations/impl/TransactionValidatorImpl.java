package com.deividlm.pismo.validations.impl;

import com.deividlm.pismo.exceptions.InvalidAmountValueException;
import com.deividlm.pismo.validations.TransactionValidator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TransactionValidatorImpl implements TransactionValidator {

    @Override
    public boolean validateAmountValue(BigDecimal amountValue){
        if(amountValue.compareTo(BigDecimal.ZERO) <= 0){
            throw new InvalidAmountValueException("The amount value must be grater than zero");
        }

        return true;
    }
}
