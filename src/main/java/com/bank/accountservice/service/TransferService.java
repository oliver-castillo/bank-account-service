package com.bank.accountservice.service;

import com.bank.accountservice.exception.BadRequestException;
import com.bank.accountservice.exception.NotFoundException;
import com.bank.accountservice.mapper.TransactionMapper;
import com.bank.accountservice.model.document.Account;
import com.bank.accountservice.model.document.Transaction;
import com.bank.accountservice.model.dto.request.transaction.TransferRequest;
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
public class TransferService implements TransactionService<TransferRequest>, TransactionCounter {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final TransactionMapper mapper;

    @Override
    public Mono<OperationResponse> save(TransferRequest request) {
        Transaction mappedTransaction = mapper.toDocument(request);
        return makeTransfer(request)
                .flatMap(transactionFee -> {
                    mappedTransaction.setTransactionFee(transactionFee);
                    return transactionRepository.save(mappedTransaction);
                })
                .doOnSuccess(document -> log.info("Transaction {} created successfully", document.getId()))
                .doOnError(error -> log.error("Error creating transaction: {}", error.getMessage()))
                .doOnTerminate(() -> log.info("Transaction creation finished"))
                .map(transaction -> new OperationResponse("Se realizó la transferencia exitosamente", HttpStatus.CREATED));
    }

    private Mono<Double> makeTransfer(TransferRequest request) {
        Mono<Account> sourceAccount = accountRepository.findAccountByAccountNumber(request.getSourceAccountNumber())
                .switchIfEmpty(Mono.error(new NotFoundException("La cuenta de origen no existe o está deshabilitada")));
        Mono<Account> destinationAccount = accountRepository.findAccountByAccountNumber(request.getDestinationAccountNumber())
                .switchIfEmpty(Mono.error(new NotFoundException("La cuenta de destino no existe o está deshabilitada")));
        return Mono.zip(sourceAccount, destinationAccount, countTransactions(request.getSourceAccountNumber()))
                .flatMap(tuple -> {
                    Account source = tuple.getT1();
                    Account destination = tuple.getT2();
                    Long numberOfTransactions = tuple.getT3();
                    if (source.canMakeTransfer(request.getAmount(), numberOfTransactions)) {
                        Double transactionFee = source.calculateTransactionFee(numberOfTransactions);
                        double transferAmount = request.getAmount();
                        source.setBalance((source.getBalance() - transferAmount) - transactionFee);
                        destination.setBalance(destination.getBalance() + transferAmount);
                        return Mono.zip(accountRepository.save(source), accountRepository.save(destination)).then(Mono.just(transactionFee));
                    } else {
                        return Mono.error(new BadRequestException("No se puede hacer la transferencia"));
                    }
                });
    }

    @Override
    public TransactionRepository getTransactionRepository() {
        return this.transactionRepository;
    }
}
