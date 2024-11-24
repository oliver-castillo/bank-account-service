package com.bank.accountservice.model.dto.request.transaction;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class TransactionRequest {
    @NotNull
    private Double amount;
}
