package com.bank.accountservice.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BusinessCheckingAccountRequest extends BusinessAccountRequest {
    @NotNull
    private Double maintenanceFee;
}
