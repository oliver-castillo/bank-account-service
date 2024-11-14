package com.bank.accountservice.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
public class FixedTermAccountRequest extends AccountRequest {
    private Set<LocalDate> allowedTransactionDates;
    private String personalClientId;
}
