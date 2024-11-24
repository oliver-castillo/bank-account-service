package com.bank.accountservice.mapper;

import com.bank.accountservice.model.document.Transaction;
import com.bank.accountservice.model.document.transaction.Deposit;
import com.bank.accountservice.model.document.transaction.Transfer;
import com.bank.accountservice.model.document.transaction.Withdrawal;
import com.bank.accountservice.model.dto.request.transaction.DepositRequest;
import com.bank.accountservice.model.dto.request.transaction.TransactionRequest;
import com.bank.accountservice.model.dto.request.transaction.TransferRequest;
import com.bank.accountservice.model.dto.request.transaction.WithdrawalRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    Transfer toDocument(TransferRequest request);

    Withdrawal toDocument(WithdrawalRequest request);

    Deposit toDocument(DepositRequest request);

    default Transaction toDocument(TransactionRequest request) {
        if (request instanceof TransferRequest object) {
            return toDocument(object);
        } else if (request instanceof WithdrawalRequest object) {
            return toDocument(object);
        } else if (request instanceof DepositRequest object) {
            return toDocument(object);
        } else {
            throw new IllegalArgumentException("Tipo de transacción no válido");
        }
    }
}
