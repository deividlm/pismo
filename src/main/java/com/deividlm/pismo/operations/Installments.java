package com.deividlm.pismo.operations;

import com.deividlm.pismo.dtos.TransactionDto;
import com.deividlm.pismo.enums.TransactionType;
import com.deividlm.pismo.models.TransactionModel;
import com.deividlm.pismo.repositories.TransactionRepository;
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

    @Autowired
    public Installments (TransactionRepository transactionRepository,ModelMapper modelMapper){
        this.transactionRepository = transactionRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public TransactionModel makeOperation(TransactionDto transactionDto) {
        TransactionModel transactionModel = modelMapper.map(transactionDto,TransactionModel.class);
        transactionModel.setAmount(transactionDto.getAmount().negate());
        transactionModel.setEventDate(LocalDateTime.now());

        transactionModel = transactionRepository.save(transactionModel);
        log.debug("Installments transaction saved successfully");
        return transactionModel;
    }

    @Override
    public String getOperationName() {
        return TransactionType.INSTALLMENTS.name();
    }
}
