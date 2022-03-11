package com.deividlm.pismo.controllers;

import com.deividlm.pismo.dtos.AccountDto;
import com.deividlm.pismo.models.AccountModel;
import com.deividlm.pismo.services.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/accounts")
public class AccountController {

    private AccountService accountService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }


    @Operation(summary = "Create a new account")
    @ApiResponses(value = {
                    @ApiResponse(responseCode = "201", description = "Account created",
                            content = { @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AccountDto.class)) }),
                    @ApiResponse(responseCode = "409", description = "Any inconsistent data",
                            content = @Content),
                     })
    @PostMapping
    public ResponseEntity<Object> createAccount(@Validated @RequestBody AccountDto accountDto){
        if(accountService.existsByDocumentNumber(accountDto.getDocumentNumber())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Account with this document number already taken!");
        }
        AccountModel accountModel = accountService.createAccount(accountDto);
        accountDto.setAccountId(accountModel.getAccountId());
        log.info("Account created successful! {}",accountDto.getAccountId());

        //add hateoas self link
        accountDto.add(linkTo(methodOn(AccountController.class).getOneAccount(accountDto.getAccountId())).withSelfRel());
         return ResponseEntity.status(HttpStatus.CREATED).body(accountDto);
    }

    @Operation(summary = "Find an account by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Account not found",
                    content = @Content),
    })

    @GetMapping(path = "/{accountId}")
    public ResponseEntity<Object> getOneAccount(@PathVariable (value = "accountId") UUID accountId){
        log.info("Searching for account with id = {}",accountId);
        Optional<AccountModel> accountModelOptional = accountService.findById(accountId);

        if(!accountModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(accountModelOptional.get());
        }

    }

}
