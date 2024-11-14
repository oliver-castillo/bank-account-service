package com.bank.accountservice.model.document.parents;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Cuenta Corriente Base
 */

@Getter
@Setter
@Document(collection = "checking-accounts")
public abstract class CheckingAccountDocument extends AccountDocument {
    private Double maintenanceFee;
}
