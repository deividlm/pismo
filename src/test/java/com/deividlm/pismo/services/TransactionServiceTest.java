package com.deividlm.pismo.services;

import com.deividlm.pismo.dtos.TransactionDto;
import com.deividlm.pismo.enums.TransactionType;
import com.deividlm.pismo.exceptions.InvalidAmountValueException;
import com.deividlm.pismo.exceptions.ResourceNotFoundException;
import com.deividlm.pismo.exceptions.UnavailableCreditLimitException;
import com.deividlm.pismo.models.AccountModel;
import com.deividlm.pismo.models.TransactionModel;
import com.deividlm.pismo.operations.OperationFactory;
import com.deividlm.pismo.operations.Withdrawn;
import com.deividlm.pismo.repositories.AccountRepository;
import com.deividlm.pismo.repositories.TransactionRepository;
import com.deividlm.pismo.validations.TransactionValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;



@SpringBootTest
public class TransactionServiceTest {

    @MockBean
    private TransactionRepository transactionRepository;

    @MockBean
    private TransactionModel transactionModel;

    @Autowired
    private TransactionService transactionService;

    @MockBean
    private TransactionDto transactionDto;

    @MockBean
    private OperationFactory operationFactory;

    @MockBean
    private AccountRepository accountRepository;

    private UUID accountId = UUID.fromString("dc097533-0b83-486e-ae03-0db07386fbf0");

    private AccountModel accountModel;

    private Optional<AccountModel>  accountModelOptional;

    @SpyBean
    private Withdrawn operation ;

    @SpyBean
    private TransactionValidator transactionValidator;

    @BeforeEach
    void setUp(){
        initTransaction();
    }

    private void initTransaction() {

        this.transactionDto = new TransactionDto();
        this.transactionDto.setTransactionType(TransactionType.WITHDRAWN.name());
        this.transactionDto.setAccountId(accountId);
        this.transactionDto.setAmount(new BigDecimal("100"));

        this.accountModel = new AccountModel();
        accountModel.setAccountId(accountId);
        this.transactionModel = new TransactionModel();
        this.transactionModel.setTransactionType(TransactionType.WITHDRAWN);
        this.transactionModel.setAccount(accountModel);
        this.transactionModel.setAmount(new BigDecimal("100"));

        this.accountModelOptional = Optional.of(accountModel);

    }

    @Test
    void whenCreateNewTransactionThenSuccessful(){
        given(transactionRepository.save(any())).willReturn(transactionModel);
        given(operationFactory.findOperation(any())).willReturn(operation);
        given(accountRepository.findById(any())).willReturn(accountModelOptional);

        TransactionModel transactionModelResult = transactionService.makeTransaction(transactionDto);

        assertNotNull(transactionModelResult);
        assertEquals(transactionModelResult.getTransactionId(), transactionDto.getTransactionId());
        assertEquals(transactionModelResult.getAccount().getAccountId(), transactionDto.getAccountId());

    }

    @Test
    void whenCreateNewTransactionThenThrowsInvalidAmountValueException(){
        given(transactionValidator.validateAmountValue(any())).willThrow(InvalidAmountValueException.class);

        try {
            transactionService.makeTransaction(transactionDto);
        } catch (Exception e) {
            assertEquals(InvalidAmountValueException.class, e.getClass());
        }

    }

    @Test
    void whenCreateNewTransactionThenThrowsResourceNotFoundException(){
        given(operationFactory.findOperation(any())).willThrow(ResourceNotFoundException.class);
        try {
            transactionService.makeTransaction(transactionDto);
        } catch (Exception e) {
            assertEquals(ResourceNotFoundException.class, e.getClass());
        }

    }

    @Test
    void whenCreateNewTransactionThrowsUnavailableCreditLimit(){
        this.accountModel.setAvailableCreditLimit(BigDecimal.TEN);
        given(transactionRepository.save(any())).willReturn(transactionModel);
        given(operationFactory.findOperation(any())).willReturn(operation);
        given(accountRepository.findById(any())).willReturn(accountModelOptional);

        Exception exception = assertThrows(UnavailableCreditLimitException.class, () -> {
           this.transactionService.makeTransaction(transactionDto);
        });

    }

}
