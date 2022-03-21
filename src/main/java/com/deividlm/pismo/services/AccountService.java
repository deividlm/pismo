package com.deividlm.pismo.services;

import com.deividlm.pismo.dtos.AccountDto;
import com.deividlm.pismo.models.AccountModel;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
public interface AccountService {

    Optional<AccountModel> findById(UUID accountId);
    AccountModel createAccount(AccountDto accountDto);
    public boolean existsByDocumentNumber(String documentNumber);
    void updateCreditLimit(BigDecimal amount, UUID accountID);


}
