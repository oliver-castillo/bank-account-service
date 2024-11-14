package com.bank.accountservice.model.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class Holder {
    @Id
    private String id;
    private String name;
    private String paternalSurname;
    private String maternalSurname;
    private String dni;
}
