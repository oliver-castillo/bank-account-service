package com.bank.accountservice.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class CheckingAccountRequest extends AccountRequest {
    private Double maintenanceFee;
}
