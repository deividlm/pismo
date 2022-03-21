package com.deividlm.pismo.validations.impl;

import com.deividlm.pismo.exceptions.InvalidAmountValueException;
import com.deividlm.pismo.exceptions.ResourceNotFoundException;
import com.deividlm.pismo.exceptions.UnavailableCreditLimitException;
import com.deividlm.pismo.models.AccountModel;
import com.deividlm.pismo.services.AccountService;
import com.deividlm.pismo.validations.TransactionValidator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class TransactionValidatorImpl implements TransactionValidator {

    private AccountService accountService;

    TransactionValidatorImpl(AccountService accountService){
        this.accountService = accountService;
    }

    @Override
    public boolean validateAmountValue(BigDecimal amountValue){
        if(amountValue.compareTo(BigDecimal.ZERO) <= 0){
            throw new InvalidAmountValueException("The amount value must be grater than zero");
        }

        return true;
    }

    @Override
    public boolean checkAvailableCreditLimit(BigDecimal amount, UUID accountId) {
        AccountModel accountModel = accountService.findById(accountId).orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        if(amount.compareTo(accountModel.getAvailableCreditLimit()) > 0){
            throw  new UnavailableCreditLimitException("Unavailable credit limit");
        }
        return false;
    }
}
