package com.bank.accountservice.model.document;

import com.bank.accountservice.model.document.parents.CheckingAccountDocument;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Cuenta Corriente Personal
 */

@AllArgsConstructor
@Getter
@Setter
//@Document(collection = "personal_checking_accounts")
public class PersonalCheckingAccountDocument extends CheckingAccountDocument {
    private Holder holder;
    private Signatory signatory;
}
