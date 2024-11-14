package com.bank.accountservice.model.document;

import com.bank.accountservice.model.document.parents.AccountDocument;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Set;

/**
 * Documento de Cuenta Plazo Fijo
 */

@AllArgsConstructor
@Getter
@Setter
@Document(collection = "fixed_term_accounts")
public class FixedTermAccountDocument extends AccountDocument {
    private Set<LocalDate> allowedTransactionDates;
    private String personalClientId;
}
