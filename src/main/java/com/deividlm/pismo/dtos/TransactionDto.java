package com.deividlm.pismo.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID transactionId;

    @NotNull(message = "Account ID must not be null")
    private UUID accountId;

    @NotBlank(message = "Transaction type must not be null")
    private String transactionType;

    @NotNull(message = "Amount must not be null")
    private BigDecimal amount;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy HH:mm:ss")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime eventDate;

}
