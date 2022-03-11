package com.deividlm.pismo.validations;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public interface TransactionValidator {

    boolean validateAmountValue(BigDecimal amountValue);
}
