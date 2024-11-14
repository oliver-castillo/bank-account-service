package com.bank.accountservice.model.document;

import com.bank.accountservice.model.document.parents.CheckingAccountDocument;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * Cuenta Corriente Empresarial
 */

@AllArgsConstructor
@Getter
@Setter
//@Document(collection = "business_checking_accounts")
public class BusinessCheckingAccountDocument extends CheckingAccountDocument {
    private Set<Holder> holders;
    private Set<Signatory> signatories;
}
