package com.bank.accountservice.service;

import com.bank.accountservice.exception.BadRequestException;
import com.bank.accountservice.exception.NotFoundException;
import com.bank.accountservice.mapper.TransactionMapper;
import com.bank.accountservice.model.document.Account;
import com.bank.accountservice.model.document.Transaction;
import com.bank.accountservice.model.dto.request.transaction.DepositRequest;
import com.bank.accountservice.model.dto.response.OperationResponse;
import com.bank.accountservice.repository.AccountRepository;
import com.bank.accountservice.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Service
public class DepositService implements TransactionService<DepositRequest>, TransactionCounter {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final TransactionMapper mapper;

    @Override
    public Mono<OperationResponse> save(DepositRequest request) {
        Transaction mappedTransaction = mapper.toDocument(request);
        return makeDeposit(request)
                .flatMap(transactionFee -> {
                    mappedTransaction.setTransactionFee(transactionFee);
                    return transactionRepository.save(mappedTransaction);
                })
                .doOnSuccess(document -> log.info("Transaction {} created successfully", document.getId()))
                .doOnError(error -> log.error("Error creating transaction: {}", error.getMessage()))
                .doOnTerminate(() -> log.info("Transaction creation finished"))
                .map(transaction -> new OperationResponse("Se realizó el depósito exitosamente", HttpStatus.CREATED));
    }

    private Mono<Double> makeDeposit(DepositRequest request) {
        Mono<Account> destinationAccount = accountRepository.findAccountByAccountNumber(request.getDestinationAccountNumber())
                .switchIfEmpty(Mono.error(new NotFoundException("La cuenta de destino no existe ó está deshabilitada")));
        return Mono.zip(destinationAccount, countTransactions(request.getDestinationAccountNumber()))
                .flatMap(tuple -> {
                    Account account = tuple.getT1();
                    Long numberOfTransactions = tuple.getT2();
                    if (account.canMakeDeposit(request.getAmount(), numberOfTransactions)) {
                        double transactionFee = account.calculateTransactionFee(numberOfTransactions);
                        double depositAmount = request.getAmount() - transactionFee;
                        account.setBalance(account.getBalance() + depositAmount);
                        return accountRepository.save(account).then(Mono.just(transactionFee));
                    } else {
                        return Mono.error(new BadRequestException("No se puede hacer el depósito"));
                    }
                });
    }

    @Override
    public TransactionRepository getTransactionRepository() {
        return this.transactionRepository;
    }
}
