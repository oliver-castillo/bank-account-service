package com.bank.accountservice.model.document.business;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Holder {
    private String name;
    private String paternalSurname;
    private String maternalSurname;
    private String dni;
}
