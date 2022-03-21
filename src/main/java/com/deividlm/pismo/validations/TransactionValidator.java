package com.deividlm.pismo.validations;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public interface TransactionValidator {

    boolean validateAmountValue(BigDecimal amountValue);
    boolean checkAvailableCreditLimit(BigDecimal amount, UUID accountId);
}
