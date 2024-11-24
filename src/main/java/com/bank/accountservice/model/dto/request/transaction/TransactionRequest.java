package com.bank.accountservice.model.dto.request.transaction;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/* *
 * Tipos de Transacciones
 * 1. Transferencia
 * 2. Retiro
 * 3. Deposito
 */

/*@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = TransferRequest.class, name = "1"),
        @JsonSubTypes.Type(value = WithdrawalRequest.class, name = "2"),
        @JsonSubTypes.Type(value = DepositRequest.class, name = "3")
})*/
@Getter
@Setter
public abstract class TransactionRequest {
   /* @NotNull
    @Range(min = 1, max = 3)
    private Integer type;*/

    @NotNull
    private Double amount;
}
