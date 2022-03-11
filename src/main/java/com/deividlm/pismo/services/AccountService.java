package com.deividlm.pismo.services;

import com.deividlm.pismo.dtos.AccountDto;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import com.deividlm.pismo.models.*;
import com.deividlm.pismo.repositories.*;

@Service
public interface AccountService {



    Optional<AccountModel> findById(UUID accountId);
    AccountModel createAccount(AccountDto accountDto);
    boolean existsByDocumentNumber(String documentNumber);

}
