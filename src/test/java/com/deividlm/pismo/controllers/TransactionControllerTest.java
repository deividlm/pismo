package com.deividlm.pismo.controllers;

import com.deividlm.pismo.dtos.AccountDto;
import com.deividlm.pismo.dtos.TransactionDto;
import com.deividlm.pismo.enums.TransactionType;
import com.deividlm.pismo.models.AccountModel;
import com.deividlm.pismo.models.TransactionModel;
import com.deividlm.pismo.repositories.AccountRepository;
import com.deividlm.pismo.repositories.TransactionRepository;
import com.deividlm.pismo.services.AccountService;
import com.deividlm.pismo.services.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TransactionController.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TransactionService transactionService;

    @MockBean
    private TransactionRepository transactionRepository;

    @MockBean
    private ModelMapper modelMapper;


    @Test
    void whenCreateTransactionValidUrlAndMethodAndContentType_thenReturns200() throws Exception {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setTransactionType(TransactionType.CASH.name());
        transactionDto.setAccountId(UUID.randomUUID());
        transactionDto.setAmount(new BigDecimal("100"));

        mockMvc.perform(post("/transactions")
                .content(objectMapper.writeValueAsString(transactionDto))
                .contentType("application/json"))
                .andExpect(status().isCreated() );
    }

    @Test
    void whenCreateTransactionWithAccountIdNull_thenReturn400() throws Exception {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setTransactionType(TransactionType.CASH.name());
        transactionDto.setAmount(new BigDecimal("100"));

        mockMvc.perform(post("/transactions")
                .content(objectMapper.writeValueAsString(transactionDto))
                .contentType("application/json"))
                .andExpect(status().isBadRequest() );
    }

    @Test
    void whenCreateTransactionWithTransactionype_thenReturn400() throws Exception {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setAccountId(UUID.randomUUID());
        transactionDto.setAmount(new BigDecimal("100"));

        mockMvc.perform(post("/transactions")
                .content(objectMapper.writeValueAsString(transactionDto))
                .contentType("application/json"))
                .andExpect(status().isBadRequest() );
    }

    @Test
    void whenCreateTransactionWithAmountNull_thenReturn400() throws Exception {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setAccountId(UUID.randomUUID());
        transactionDto.setTransactionType(TransactionType.CASH.name());

        mockMvc.perform(post("/transactions")
                .content(objectMapper.writeValueAsString(transactionDto))
                .contentType("application/json"))
                .andExpect(status().isBadRequest() );
    }


}
