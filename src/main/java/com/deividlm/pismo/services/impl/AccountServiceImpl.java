package com.deividlm.pismo.services.impl;

import com.deividlm.pismo.dtos.AccountDto;
import com.deividlm.pismo.exceptions.ResourceNotFoundException;
import com.deividlm.pismo.exceptions.UniqueViolationException;
import com.deividlm.pismo.models.AccountModel;
import com.deividlm.pismo.repositories.AccountRepository;
import com.deividlm.pismo.services.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;
    private ModelMapper modelMapper;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository,ModelMapper modelMapper ){
        this.accountRepository = accountRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Optional<AccountModel>  findById(UUID accountId) {
        return accountRepository.findById(accountId);
    }

    @Override
    public AccountModel createAccount(AccountDto accountDto) {
        if(existsByDocumentNumber(accountDto.getDocumentNumber())){
            throw new UniqueViolationException("Account with this document number already taken!");
        }
        return accountRepository.save(modelMapper.map(accountDto, AccountModel.class) );
    }

    @Override
    public boolean existsByDocumentNumber(String documentNumber) {
        return accountRepository.existsByDocumentNumber(documentNumber);
    }

    @Override
    public void updateCreditLimit(BigDecimal amount, UUID accountID) {
        AccountModel accountModel = accountRepository.findById(accountID).orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        accountModel.setAvailableCreditLimit(accountModel.getAvailableCreditLimit().add(amount));
        accountRepository.save(accountModel);
    }

}
