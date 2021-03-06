package com.deividlm.pismo.services.impl;

import com.deividlm.pismo.dtos.TransactionDto;
import com.deividlm.pismo.exceptions.ResourceNotFoundException;
import com.deividlm.pismo.models.TransactionModel;
import com.deividlm.pismo.operations.Operation;
import com.deividlm.pismo.operations.OperationFactory;
import com.deividlm.pismo.repositories.AccountRepository;
import com.deividlm.pismo.services.AccountService;
import com.deividlm.pismo.services.TransactionService;
import com.deividlm.pismo.validations.TransactionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionServiceImpl implements TransactionService {

    private OperationFactory operationFactory;
    private AccountRepository accountRepository;
    private TransactionValidator transactionValidator;
    private AccountService accountService;

    @Autowired
    public TransactionServiceImpl(OperationFactory operationFactory,
                                  AccountRepository accountRepository,
                                  TransactionValidator transactionValidator,
                                  AccountService accountService){
        this.operationFactory = operationFactory;
        this.accountRepository = accountRepository;
        this.transactionValidator = transactionValidator;
        this.accountService = accountService;
    }

    @Override
    @Transactional
    public TransactionModel makeTransaction(TransactionDto transactionDto) {
        Operation operation = operationFactory.findOperation(transactionDto.getTransactionType());

        transactionValidator.validateAmountValue(transactionDto.getAmount());
        if(operation == null){
            throw new ResourceNotFoundException("Transaction type not found");
        }
        if(!this.accountRepository.findById(transactionDto.getAccountId()).isPresent()){
            throw new ResourceNotFoundException("Account not found");
        }

        TransactionModel transactionModel = operation.makeOperation(transactionDto);
        accountService.updateCreditLimit(transactionModel.getAmount(),transactionDto.getAccountId());


        return transactionModel;
    }
}
