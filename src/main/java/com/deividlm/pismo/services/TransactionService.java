package com.deividlm.pismo.services;

import com.deividlm.pismo.dtos.TransactionDto;
import com.deividlm.pismo.models.TransactionModel;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public interface TransactionService {
    TransactionModel makeTransaction(TransactionDto transactionDto);
}
