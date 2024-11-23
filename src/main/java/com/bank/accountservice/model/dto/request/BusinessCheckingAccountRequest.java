package com.bank.accountservice.model.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BusinessCheckingAccountRequest extends BusinessAccountRequest {
    @NotNull
    private Double maintenanceFee;

    @NotEmpty
    @Valid
    @Size(min = 1)
    private Set<HolderRequest> holders;
    
    @Valid
    private Set<SignatoryRequest> signatories = Set.of();
}
