package com.deividlm.pismo.repositories;

import com.deividlm.pismo.models.AccountModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
class AccountRepositoryTest {


    private AccountModel accountModel;


    @Autowired
    private AccountRepository accountRepository;


    @BeforeEach
    public void setUp() {
        accountModel = new AccountModel();
        accountModel.setDocumentNumber("1234568");
    }

    @Test
    public void whenCreateNewAccountThenReturnSuccess() {
        accountRepository.save(accountModel);
        AccountModel accountModelResult = accountRepository.findById(accountModel.getAccountId()).get();

        assertNotNull(accountModelResult);
        assertEquals(accountModel.getDocumentNumber(), accountModelResult.getDocumentNumber());
    }

    @Test

    public void whenFindByIdThenReturnAnAccount() {
        accountModel.setDocumentNumber("12345689");
        AccountModel accountModelSaved = accountRepository.save(accountModel);
        Optional<AccountModel> optional =  accountRepository.findById(accountModelSaved.getAccountId());
        assertEquals(accountModelSaved.getAccountId(), optional.get().getAccountId());
    }

}