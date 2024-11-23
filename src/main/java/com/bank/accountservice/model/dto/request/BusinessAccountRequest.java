package com.bank.accountservice.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BusinessAccountRequest extends AccountRequest {
    /*@Size(min = 1)
    @Valid
    private Set<HolderRequest> holders;
    @Valid
    private Set<SignatoryRequest> signatories = Set.of();*/
}
