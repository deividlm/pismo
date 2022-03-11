package com.deividlm.pismo.operations;

import com.deividlm.pismo.dtos.TransactionDto;
import com.deividlm.pismo.enums.TransactionType;
import com.deividlm.pismo.models.TransactionModel;
import org.springframework.stereotype.Component;

@Component
public interface Operation {
    TransactionModel makeOperation(TransactionDto transactionDto) ;

    String getOperationName();
}
