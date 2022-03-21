package com.deividlm.pismo.operations;

import com.deividlm.pismo.dtos.TransactionDto;
import com.deividlm.pismo.enums.TransactionType;
import com.deividlm.pismo.models.TransactionModel;
import com.deividlm.pismo.repositories.TransactionRepository;
import com.deividlm.pismo.validations.TransactionValidator;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Log4j2
public class Installments implements Operation{

    private TransactionRepository transactionRepository;
    private ModelMapper modelMapper;
    private TransactionValidator transactionValidator;


    @Autowired
    public Installments (TransactionRepository transactionRepository,
                         ModelMapper modelMapper,
                         TransactionValidator transactionValidator){
        this.transactionRepository = transactionRepository;
        this.modelMapper = modelMapper;
        this.transactionValidator = transactionValidator;
    }


    @Override
    public TransactionModel makeOperation(TransactionDto transactionDto) {
        TransactionModel transactionModel = modelMapper.map(transactionDto,TransactionModel.class);

        transactionModel.setEventDate(LocalDateTime.now());

        transactionValidator.checkAvailableCreditLimit(transactionDto.getAmount(), transactionDto.getAccountId());

        transactionModel.setAmount(transactionDto.getAmount().negate());
        transactionModel = transactionRepository.save(transactionModel);
        log.debug("Installments transaction saved successfully");
        return transactionModel;
    }

    @Override
    public String getOperationName() {
        return TransactionType.INSTALLMENTS.name();
    }
}
