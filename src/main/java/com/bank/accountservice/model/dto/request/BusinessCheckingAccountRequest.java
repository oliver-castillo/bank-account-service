package com.bank.accountservice.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
public class BusinessCheckingAccountRequest extends CheckingAccountRequest {
    private Set<HolderRequest> holders;
    private Set<SignatoryRequest> signatories;
}
