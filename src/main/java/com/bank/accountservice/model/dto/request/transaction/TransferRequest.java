package com.bank.accountservice.model.dto.request.transaction;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransferRequest extends TransactionRequest {
    @NotNull
    private String sourceAccountNumber;

    @NotNull
    private String destinationAccountNumber;
}
