package com.deividlm.pismo.services.impl;

import com.deividlm.pismo.dtos.AccountDto;
import com.deividlm.pismo.services.AccountService;

import java.util.Optional;
import java.util.UUID;
import com.deividlm.pismo.models.*;
import com.deividlm.pismo.repositories.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return accountRepository.save(modelMapper.map(accountDto, AccountModel.class) );
    }

    @Override
    public boolean existsByDocumentNumber(String documentNumber) {
        return accountRepository.existsByDocumentNumber(documentNumber);
    }
}
