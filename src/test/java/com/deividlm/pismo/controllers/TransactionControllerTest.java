package com.deividlm.pismo.controllers;

import com.deividlm.pismo.dtos.TransactionDto;
import com.deividlm.pismo.enums.TransactionType;
import com.deividlm.pismo.services.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TransactionController.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    TransactionService transactionService;

    @Test
    void whenCreateTransactionValidUrlAndMethodAndContentTypeThenReturnsOk() throws Exception {
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
    void whenCreateTransactionWithAccountIdNullThenReturnBadRequest() throws Exception {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setTransactionType(TransactionType.CASH.name());
        transactionDto.setAmount(new BigDecimal("100"));

        mockMvc.perform(post("/transactions")
                .content(objectMapper.writeValueAsString(transactionDto))
                .contentType("application/json"))
                .andExpect(status().isBadRequest() );
    }

    @Test
    void whenCreateTransactionWithTransactionTypeThenReturnBadRequest() throws Exception {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setAccountId(UUID.randomUUID());
        transactionDto.setAmount(new BigDecimal("100"));

        mockMvc.perform(post("/transactions")
                .content(objectMapper.writeValueAsString(transactionDto))
                .contentType("application/json"))
                .andExpect(status().isBadRequest() );
    }

    @Test
    void whenCreateTransactionWithAmountNullThenReturnBadRequest() throws Exception {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setAccountId(UUID.randomUUID());
        transactionDto.setTransactionType(TransactionType.CASH.name());

        mockMvc.perform(post("/transactions")
                .content(objectMapper.writeValueAsString(transactionDto))
                .contentType("application/json"))
                .andExpect(status().isBadRequest() );
    }


}
