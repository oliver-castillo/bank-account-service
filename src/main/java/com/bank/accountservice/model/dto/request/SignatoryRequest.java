package com.bank.accountservice.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignatoryRequest(
        @NotBlank
        String name,
        @NotBlank
        String paternalSurname,
        @NotBlank
        String maternalSurname,
        @NotBlank
        @Size(min = 8, max = 8)
        String dni) {
}