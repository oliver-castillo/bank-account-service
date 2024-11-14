package com.bank.accountservice.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class HolderRequest {
    private String name;
    private String paternalSurname;
    private String maternalSurname;
    private String dni;
}