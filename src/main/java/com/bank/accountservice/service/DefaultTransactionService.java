package com.bank.accountservice.service;

import com.bank.accountservice.exception.BadRequestException;
import com.bank.accountservice.exception.NotFoundException;
import com.bank.accountservice.mapper.TransactionMapper;
import com.bank.accountservice.model.document.Account;
import com.bank.accountservice.model.document.transaction.Withdrawal;
import com.bank.accountservice.model.dto.request.transaction.DepositRequest;
import com.bank.accountservice.model.dto.request.transaction.TransactionRequest;
import com.bank.accountservice.model.dto.request.transaction.TransferRequest;
import com.bank.accountservice.model.dto.request.transaction.WithdrawalRequest;
import com.bank.accountservice.model.dto.response.OperationResponse;
import com.bank.accountservice.model.enums.TransactionType;
import com.bank.accountservice.repository.AccountRepository;
import com.bank.accountservice.repository.TransactionRepository;
import com.bank.accountservice.util.ResponseMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultTransactionService implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final TransactionMapper mapper;

    @Override
    public Mono<OperationResponse> save(TransactionRequest request) {
        return makeTransaction(request)
                .then(transactionRepository.save(mapper.toDocument(request)))
                .doOnSuccess(document -> log.info("Transaction {} created successfully", document.getId()))
                .doOnError(error -> log.error("Error creating transaction: {}", error.getMessage()))
                .doOnTerminate(() -> log.info("Transaction creation finished"))
                .map(transaction -> new OperationResponse(ResponseMessage.CREATED_SUCCESSFULLY, HttpStatus.CREATED));
    }

    private Mono<Void> makeTransaction(TransactionRequest request) {
        return Mono.just(request)
                .flatMap(request1 -> {
                    if (request1 instanceof TransferRequest transfer) {
                        return makeTransfer(transfer);
                    } else if (request1 instanceof WithdrawalRequest withdrawal) {
                        return makeWithdrawal(withdrawal, countTransactions(withdrawal.getSourceAccountNumber()));
                    } else if (request1 instanceof DepositRequest deposit) {
                        return makeDeposit(deposit, countTransactions(deposit.getDestinationAccountNumber()));
                    } else {
                        return Mono.error(new BadRequestException("Tipo de transacción no válido"));
                    }
                });
    }

    private Mono<Account> findAccountByAccountNumber(String accountNumber) {
        return accountRepository.findAccountByAccountNumber(accountNumber)
                .switchIfEmpty(Mono.error(new NotFoundException(ResponseMessage.NOT_FOUND.getMessage())));
    }


    private Mono<Void> makeDeposit(DepositRequest request, Long numberOfTransactions) {
        String destinationAccountNumber = request.getDestinationAccountNumber();
        return findAccountByAccountNumber(destinationAccountNumber)
                .doOnNext(account -> {
                    if (account.canMakeDeposit(request.getAmount(), numberOfTransactions)) {
                        double depositAmount = request.getAmount() - account.calculateTransactionFee(numberOfTransactions);
                        account.setBalance(account.getBalance() + depositAmount);
                    } else {
                        throw new BadRequestException("No puede hacer el depósito");
                    }
                })
                .doOnNext(accountRepository::save)
                .then();
    }

    private Mono<Void> makeWithdrawal(WithdrawalRequest request, Long numberOfTransactions) {
        String destinationAccountNumber = request.getSourceAccountNumber();
        return findAccountByAccountNumber(destinationAccountNumber)
                .doOnNext(account -> {
                    if (account.canMakeWithdrawal(request.getAmount(), numberOfTransactions)) {
                        double withdrawalAmount = request.getAmount() + account.calculateTransactionFee(numberOfTransactions);
                        account.setBalance(account.getBalance() - withdrawalAmount);
                    } else {
                        throw new BadRequestException("No puede hacer el retiro");
                    }
                })
                .doOnNext(accountRepository::save)
                .then();
    }

    private Mono<Void> makeTransfer(TransferRequest request) {
        Mono<Account> sourceAccount = findAccountByAccountNumber(request.getSourceAccountNumber());
        Mono<Account> destinationAccount = findAccountByAccountNumber(request.getDestinationAccountNumber());
        return Mono.zip(sourceAccount, destinationAccount)
                .flatMap(tuple -> {
                    Account source = tuple.getT1();
                    Account destination = tuple.getT2();
                    if (!source.canMakeTransfer(request.getAmount())) {
                        return Mono.error(new BadRequestException("La cuenta no tiene suficiente saldo para hacer la transferencia"));
                    }
                    source.setBalance(source.getBalance() - request.getAmount());
                    destination.setBalance(destination.getBalance() + request.getAmount());
                    return Mono.zip(accountRepository.save(source), accountRepository.save(destination));
                })
                .then();
    }

    private Long countTransactions(String accountNumber) {
        Mono<Long> numberOfWithdrawals = transactionRepository.findTransactionsByTransactionType(TransactionType.WITHDRAWAL)
                .map(Withdrawal.class::cast)
                .filter(withdrawal -> withdrawal.getSourceAccountNumber().equals(accountNumber))
                .count();
        Mono<Long> numberOfDeposits = transactionRepository.findTransactionsByTransactionType(TransactionType.DEPOSIT)
                .map(DepositRequest.class::cast)
                .filter(deposit -> deposit.getDestinationAccountNumber().equals(accountNumber))
                .count();
        return Flux.merge(numberOfWithdrawals, numberOfDeposits).reduce(Long::sum).block();
    }
}
