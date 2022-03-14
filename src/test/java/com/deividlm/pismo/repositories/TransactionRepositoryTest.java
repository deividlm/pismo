package com.deividlm.pismo.repositories;

import com.deividlm.pismo.enums.TransactionType;
import com.deividlm.pismo.models.AccountModel;
import com.deividlm.pismo.models.TransactionModel;
import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TransactionRepositoryTest {

    private AccountModel accountModel;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    private TransactionModel transactionModel;

    @BeforeEach
    private void setUp(){
        this.accountModel = new AccountModel();
        this.accountModel.setDocumentNumber("123456789");
        this.transactionModel = new TransactionModel();
        this.transactionModel.setAmount(BigDecimal.TEN);
    }

    @AfterEach
    private void setDown(){
        transactionRepository.deleteAll();
        this.transactionModel = null;
        accountRepository.deleteAll();
        this.accountModel = null;
    }


    @Test
    @Order(1)
    public void whenCreatePaymentThenReturnSuccessful() {
        transactionModel.setAccount(accountRepository.save(accountModel));
        transactionModel.setTransactionType(TransactionType.PAYMENT);
        transactionModel.setEventDate(LocalDateTime.now());
        transactionRepository.save(transactionModel);

        TransactionModel transactionModelDb = transactionRepository.findById(transactionModel.getTransactionId()).get();

        assertNotNull(transactionModel);
        assertNotNull(transactionModel.getTransactionId());
        assertEquals(TransactionType.PAYMENT, transactionModel.getTransactionType());
        assertEquals(BigDecimal.TEN, transactionModel.getAmount());
    }

    @Test
    @Order(1)
    public void whenCreateCashThenReturnSuccessful() {
        transactionModel.setAccount(accountRepository.save(accountModel));
        transactionModel.setTransactionType(TransactionType.CASH);
        transactionModel.setEventDate(LocalDateTime.now());
        transactionRepository.save(transactionModel);

        TransactionModel transactionModelDb = transactionRepository.findById(transactionModel.getTransactionId()).get();

        assertNotNull(transactionModelDb);
        assertNotNull(transactionModelDb.getTransactionId());
        assertEquals(TransactionType.CASH, transactionModelDb.getTransactionType());
        assertEquals(new BigDecimal("10.00"), transactionModelDb.getAmount());
    }

    @Test
    public void whenCreateInstallmentsThenReturnSuccessful() {
        transactionModel.setAccount(accountRepository.save(accountModel));
        transactionModel.setTransactionType(TransactionType.INSTALLMENTS);
        transactionModel.setEventDate(LocalDateTime.now());
        transactionRepository.save(transactionModel);

        TransactionModel transactionModelDb = transactionRepository.findById(transactionModel.getTransactionId()).get();

        assertNotNull(transactionModel);
        assertNotNull(transactionModel.getTransactionId());
        assertEquals(TransactionType.INSTALLMENTS, transactionModel.getTransactionType());
        assertEquals(new BigDecimal("10.00"), transactionModelDb.getAmount());
    }

    @Test
    public void whenCreateWithdrawnThenReturnSuccessful() {
        transactionModel.setAccount(accountRepository.save(accountModel));
        transactionModel.setTransactionType(TransactionType.WITHDRAWN);
        transactionModel.setEventDate(LocalDateTime.now());
        transactionRepository.save(transactionModel);

        TransactionModel transactionModelDb = transactionRepository.findById(transactionModel.getTransactionId()).get();

        assertNotNull(transactionModel);
        assertNotNull(transactionModel.getTransactionId());
        assertEquals(TransactionType.WITHDRAWN, transactionModel.getTransactionType());
        assertEquals(new BigDecimal("10.00"), transactionModelDb.getAmount());
    }


}
