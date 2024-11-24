package com.bank.accountservice.model.dto.request.account;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

/**
 * Tipos de cuentas
 * 1. Personal - Cuenta de ahorro (Savings Account)
 * 2. Personal - Cuenta corriente (Checking Account)
 * 3. Personal - Cuenta plazo fijo (Fixed Term Account)
 * 4. Personal - Cuenta de ahorro VIP (VIP Savings Account)
 * 5. Empresarial - Cuenta corriente (Checking Account)
 * 6. Empresarial - Cuenta corriente Pyme (Pyme Checking Account)
 */

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = PersonalSavingsAccountRequest.class, name = "1"),
        @JsonSubTypes.Type(value = PersonalCheckingAccountRequest.class, name = "2"),
        @JsonSubTypes.Type(value = PersonalFixedTermAccountRequest.class, name = "3"),
        @JsonSubTypes.Type(value = PersonalVipSavingsAccountRequest.class, name = "4"),
        @JsonSubTypes.Type(value = BusinessCheckingAccountRequest.class, name = "5"),
        @JsonSubTypes.Type(value = BusinessPymeCheckingAccountRequest.class, name = "6")
})
@Getter
@Setter
public abstract class AccountRequest {
    @NotNull
    @Range(min = 1, max = 6)
    private Integer type;

    @NotBlank
    private String clientId;

    @NotNull
    private Double balance;

    @NotNull
    private Integer maximumCommissionFreeTransactions;

    @NotNull
    private Integer freeTransactions;

    @NotNull
    private Double transactionFee;
}