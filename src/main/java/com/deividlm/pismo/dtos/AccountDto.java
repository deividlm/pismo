package com.deividlm.pismo.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountDto extends RepresentationModel<AccountDto> {

    @JsonProperty()
    private UUID accountId;

    @NotBlank(message = "Document Number must not be null")
    private String documentNumber;

    @NotNull(message = "Credit Limit must not be null")
    private BigDecimal availableCreditLimit;


}
