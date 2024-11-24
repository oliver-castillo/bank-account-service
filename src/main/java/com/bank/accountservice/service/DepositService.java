package com.bank.accountservice.service;

import com.bank.accountservice.exception.BadRequestException;
import com.bank.accountservice.exception.NotFoundException;
import com.bank.accountservice.mapper.TransactionMapper;
import com.bank.accountservice.model.document.Account;
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
        return makeDeposit(request)
                .then(transactionRepository.save(mapper.toDocument(request)))
                .doOnSuccess(document -> log.info("Transaction {} created successfully", document.getId()))
                .doOnError(error -> log.error("Error creating transaction: {}", error.getMessage()))
                .doOnTerminate(() -> log.info("Transaction creation finished"))
                .map(transaction -> new OperationResponse("Se realizó el depósito exitosamente", HttpStatus.CREATED));
    }

    private Mono<Void> makeDeposit(DepositRequest request) {
        Mono<Account> destinationAccount = accountRepository.findAccountByAccountNumber(request.getDestinationAccountNumber())
                .switchIfEmpty(Mono.error(new NotFoundException("La cuenta de destino no existe")));
        return Mono.zip(destinationAccount, countTransactions(request.getDestinationAccountNumber()))
                .flatMap(tuple -> {
                    Account account = tuple.getT1();
                    Long numberOfTransactions = tuple.getT2();
                    if (!account.canMakeDeposit(request.getAmount(), numberOfTransactions)) {
                        return Mono.error(new BadRequestException("La cuenta no tiene suficiente saldo para hacer el depósito"));
                    }
                    double depositAmount = request.getAmount() - account.calculateTransactionFee(numberOfTransactions);
                    account.setBalance(account.getBalance() + depositAmount);
                    return accountRepository.save(account);
                }).then();
    }

    @Override
    public TransactionRepository getTransactionRepository() {
        return this.transactionRepository;
    }
}
