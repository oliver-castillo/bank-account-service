package com.bank.accountservice.model.dto.request;

import jakarta.validation.constraints.NotNull;

public record Animal
        (@NotNull
         String name) {
}
