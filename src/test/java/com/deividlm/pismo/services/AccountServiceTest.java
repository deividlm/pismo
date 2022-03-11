package com.deividlm.pismo.services;

import com.deividlm.pismo.dtos.AccountDto;
import com.deividlm.pismo.exceptions.ResourceNotFoundException;
import com.deividlm.pismo.exceptions.UniqueViolationException;
import com.deividlm.pismo.models.AccountModel;
import com.deividlm.pismo.repositories.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.validation.ConstraintViolationException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AccountServiceTest {

    @MockBean
    private AccountRepository accountRepository;

    private AccountModel accountModel;

    @Autowired
    private AccountService accountService;

    private Optional<AccountModel> accountModelOptional;

    private AccountDto accountDto;

    private UUID accountId = UUID.fromString("72f3bcb8-9f8a-438e-9c99-fd81b4eec079");

    @BeforeEach
    void setUp(){
        initAccount();
    }

    private void initAccount() {
        this.accountDto = new AccountDto();
        this.accountDto.setDocumentNumber("123456");
        this.accountDto.setAccountId(accountId);

        this.accountModel = new AccountModel();
        this.accountModel.setDocumentNumber("123456");
        this.accountModel.setAccountId(accountId);

        this.accountModelOptional = Optional.of(accountModel);
    }


    @Test
    void whenCreateNewAccountThenSuccessful(){
        given(accountRepository.save(any())).willReturn(accountModel);

        AccountModel accountModelResult = accountService.createAccount(accountDto);

        assertNotNull(accountModelResult);
        assertNotNull(accountModelResult.getAccountId());
        assertEquals(accountModelResult.getAccountId(),accountDto.getAccountId());
        assertEquals(accountModelResult.getAccountId(),accountDto.getAccountId());

    }

    @Test
    void whenCreateNewAccountThenThrowsConstraintViolationsException(){
        given(accountRepository.save(any())).willThrow(ConstraintViolationException.class);
        accountDto.setDocumentNumber(null);
        try {
            accountService.createAccount(accountDto);
        } catch (Exception e) {
            assertEquals(ConstraintViolationException.class, e.getClass());
        }

    }

    @Test
    void whenCreateNewAccountThenThrowsUniqueViolationException(){
        given(accountRepository.save(any())).willThrow(UniqueViolationException.class);
        accountDto.setDocumentNumber(null);
        try {
            accountService.createAccount(accountDto);
        } catch (Exception e) {
            assertEquals(UniqueViolationException.class, e.getClass());
        }
    }

    @Test
    void whenFindByIdThenReturnAnAccount() {
        given(accountRepository.findById(any())).willReturn(accountModelOptional);
        Optional<AccountModel> accountModelResult = accountService.findById(accountId);
        assertNotNull(accountModelResult);
        assertEquals(AccountModel.class, accountModelResult.get().getClass());
        assertEquals(accountId, accountModelResult.get().getAccountId());
    }

    @Test
    void whenFindByIdThenReturnAnResourceNotFoundException() {
        when(accountRepository.findById(accountId)).thenThrow(ResourceNotFoundException.class);
        try {
            accountService.findById(accountId);
        } catch (Exception e) {
            assertEquals(ResourceNotFoundException.class, e.getClass());
        }
    }
}
