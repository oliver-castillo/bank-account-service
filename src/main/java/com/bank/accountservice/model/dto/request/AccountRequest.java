package com.bank.accountservice.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AccountRequest {
    private String accountNumber;

    private double balance;
}
