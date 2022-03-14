package com.deividlm.pismo.controllers;

import com.deividlm.pismo.dtos.AccountDto;
import com.deividlm.pismo.models.AccountModel;
import com.deividlm.pismo.services.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AccountController.class)
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AccountService accountService;

    private AccountModel accountModel;

    @MockBean
    ModelMapper modelMapper;

    @BeforeEach
    private void setUp(){
        initAccountModel();
    }

    private void initAccountModel() {
        this.accountModel = new AccountModel();
        this.accountModel.setDocumentNumber("987654321");
        this.accountModel.setAccountId(UUID.fromString("f555474d-53a7-4cae-be12-2dd7bfaf809e"));
    }


    @Test
    void whenCreateAccountValidUrlAndMethodAndContentTypeThenReturnsOk() throws Exception {
        AccountDto accountDto = new AccountDto();
        accountDto.setDocumentNumber("123569");
        given(accountService.createAccount(any())).willReturn(accountModel);


        mockMvc.perform(post("/accounts")
                .content(objectMapper.writeValueAsString(accountDto))
                .contentType("application/json"))
                .andExpect(status().isCreated() );
    }

    @Test
    void whenCreateAccountWithDocumentTypeNullThenReturnsBadRequest() throws Exception {
        AccountDto accountDto = new AccountDto();
        given(accountService.createAccount(any())).willReturn(accountModel);

        mockMvc.perform(post("/accounts")
                .content(objectMapper.writeValueAsString(accountDto))
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGetOneAccountValidUrlAndMethodAndContentTypeThenReturnsOk() throws Exception {
        final UUID accountId = UUID.randomUUID();
        AccountModel accountModel = new AccountModel();
        accountModel.setAccountId(accountId);
        accountModel.setDocumentNumber("1003944");

        given(accountService.findById(accountId)).willReturn(Optional.of(accountModel));
        mockMvc.perform(get("/accounts/"+ accountId)
                .contentType("application/json"))
                .andExpect(status().isOk() );
    }

    @Test
    void whenAccountNotExistThenReturnNotFound() throws Exception {
        final UUID accountId = UUID.randomUUID();
        given(accountService.findById(accountId)).willReturn(Optional.empty());

        mockMvc.perform(get("/accounts/"+accountId))
                .andExpect(status().isNotFound());
    }
}
