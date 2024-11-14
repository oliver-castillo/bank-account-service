package com.bank.accountservice.model.document;

import com.bank.accountservice.model.document.parents.AccountDocument;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Documento de Cuenta de Ahorro
 */

@AllArgsConstructor
@Getter
@Setter
@Document(collection = "saving_accounts")
public class SavingAccountDocument extends AccountDocument {
    private Integer monthlyTransactionLimit;
    private String personalClientId;
}
