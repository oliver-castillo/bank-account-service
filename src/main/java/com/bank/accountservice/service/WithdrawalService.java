package com.bank.accountservice.service;

import com.bank.accountservice.exception.BadRequestException;
import com.bank.accountservice.exception.NotFoundException;
import com.bank.accountservice.mapper.TransactionMapper;
import com.bank.accountservice.model.document.Account;
import com.bank.accountservice.model.document.Transaction;
import com.bank.accountservice.model.dto.request.transaction.WithdrawalRequest;
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
public class WithdrawalService implements TransactionService<WithdrawalRequest>, TransactionCounter {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final TransactionMapper mapper;

    @Override
    public Mono<OperationResponse> makeTransaction(WithdrawalRequest request) {
        Transaction mappedTransaction = mapper.toDocument(request);
        return makeWithdrawal(request)
                .flatMap(transactionFee -> {
                    mappedTransaction.setTransactionFee(transactionFee);
                    return transactionRepository.save(mappedTransaction);
                })
                .doOnSuccess(document -> log.info("Transaction {} created successfully", document.getId()))
                .doOnError(error -> log.error("Error creating transaction: {}", error.getMessage()))
                .doOnTerminate(() -> log.info("Transaction creation finished"))
                .map(transaction -> new OperationResponse("Se realizó el retiro exitosamente", HttpStatus.CREATED));
    }

    private Mono<Double> makeWithdrawal(WithdrawalRequest request) {
        Mono<Account> sourceAccount = accountRepository.findAccountByAccountNumber(request.getSourceAccountNumber())
                .switchIfEmpty(Mono.error(new NotFoundException("La cuenta de origen no existe o está deshabilitada")));
        return Mono.zip(sourceAccount, countTransactions(request.getSourceAccountNumber()))
                .flatMap(tuple -> {
                    Account account = tuple.getT1();
                    Long numberOfTransactions = tuple.getT2();
                    if (account.canMakeWithdrawal(request.getAmount(), numberOfTransactions)) {
                        double transactionFee = account.calculateTransactionFee(numberOfTransactions);
                        double withdrawalAmount = request.getAmount();
                        account.setBalance((account.getBalance() - withdrawalAmount) - transactionFee);
                        return accountRepository.save(account).then(Mono.just(transactionFee));
                    } else {
                        return Mono.error(new BadRequestException("No se puede hacer el retiro"));
                    }
                });
    }

    @Override
    public TransactionRepository getTransactionRepository() {
        return this.transactionRepository;
    }
}
