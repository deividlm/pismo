package com.deividlm.pismo.controllers;

import com.deividlm.pismo.dtos.TransactionDto;
import com.deividlm.pismo.services.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/transactions")
public class TransactionController {

    private TransactionService transactionService;

    @Autowired
    ModelMapper modelMapper;


    @Autowired
    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @Operation(summary = "Make a new transaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transaction created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TransactionDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Account or transaction type  not found",
                    content = @Content),
    })
    @PostMapping
    public ResponseEntity<Object> makeTransaction(@Validated @RequestBody TransactionDto transactionDto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(modelMapper.map(transactionService.makeTransaction(transactionDto), TransactionDto.class));
    }

}
