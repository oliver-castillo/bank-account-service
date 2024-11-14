package com.bank.accountservice.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class SavingAccountRequest extends AccountRequest {
    private Integer monthlyTransactionLimit;

    private String personalClientId;
}
