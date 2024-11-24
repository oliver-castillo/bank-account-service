package com.bank.accountservice.service;

import com.bank.accountservice.model.document.transaction.Deposit;
import com.bank.accountservice.model.document.transaction.Transfer;
import com.bank.accountservice.model.document.transaction.Withdrawal;
import com.bank.accountservice.model.enums.TransactionType;
import com.bank.accountservice.repository.TransactionRepository;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface TransactionCounter {
    TransactionRepository getTransactionRepository();

    default Mono<Long> countTransactions(String accountNumber) {
        Mono<Long> numberOfWithdrawals = getTransactionRepository().findTransactionsByTransactionType(TransactionType.WITHDRAWAL)
                .map(Withdrawal.class::cast)
                .filter(withdrawal -> withdrawal.getSourceAccountNumber().equals(accountNumber) && withdrawal.getDate().getMonth().equals(LocalDate.now().getMonth()))
                .count();

        Mono<Long> numberOfDeposits = getTransactionRepository().findTransactionsByTransactionType(TransactionType.DEPOSIT)
                .map(Deposit.class::cast)
                .filter(deposit -> deposit.getDestinationAccountNumber().equals(accountNumber) && deposit.getDate().getMonth().equals(LocalDate.now().getMonth()))
                .count();

        Mono<Long> numberOfTransfers = getTransactionRepository().findTransactionsByTransactionType(TransactionType.TRANSFER)
                .map(Transfer.class::cast)
                .filter(deposit -> deposit.getSourceAccountNumber().equals(accountNumber) && deposit.getDate().getMonth().equals(LocalDate.now().getMonth()))
                .count();

        return Mono.zip(numberOfWithdrawals, numberOfDeposits, numberOfTransfers)
                .map(tuple -> tuple.getT1() + tuple.getT2() + tuple.getT3());
    }
}
