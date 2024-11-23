package com.bank.accountservice.model.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PersonalFixedTermAccountRequest extends AccountRequest {
    @NotEmpty
    private Set<LocalDate> allowedTransactionDates;
}
