package com.bank.accountservice.model.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public abstract class BusinessAccountRequest extends AccountRequest {
    @NotEmpty
    @Valid
    @Size(min = 1)
    private Set<HolderRequest> holders;

    @Valid
    private Set<SignatoryRequest> signatories = Set.of();
}
