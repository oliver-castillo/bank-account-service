package com.bank.accountservice.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class PersonalCheckingAccountRequest extends CheckingAccountRequest {
    private HolderRequest holder;
    private SignatoryRequest signatory;
}
