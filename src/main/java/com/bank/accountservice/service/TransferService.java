package com.bank.accountservice.service;

import com.bank.accountservice.exception.BadRequestException;
import com.bank.accountservice.exception.NotFoundException;
import com.bank.accountservice.mapper.TransactionMapper;
import com.bank.accountservice.model.document.Account;
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
        return makeTransfer(request)
                .then(transactionRepository.save(mapper.toDocument(request)))
                .doOnSuccess(document -> log.info("Transaction {} created successfully", document.getId()))
                .doOnError(error -> log.error("Error creating transaction: {}", error.getMessage()))
                .doOnTerminate(() -> log.info("Transaction creation finished"))
                .map(transaction -> new OperationResponse("Se realizó la transferencia exitosamente", HttpStatus.CREATED));
    }

    private Mono<Void> makeTransfer(TransferRequest request) {
        Mono<Account> sourceAccount = accountRepository.findAccountByAccountNumber(request.getSourceAccountNumber())
                .switchIfEmpty(Mono.error(new NotFoundException("La cuenta de origen no existe")));
        Mono<Account> destinationAccount = accountRepository.findAccountByAccountNumber(request.getDestinationAccountNumber())
                .switchIfEmpty(Mono.error(new NotFoundException("La cuenta de destino no existe")));
        return Mono.zip(sourceAccount, destinationAccount, countTransactions(request.getSourceAccountNumber()))
                .flatMap(tuple -> {
                    Account source = tuple.getT1();
                    Account destination = tuple.getT2();
                    Long numberOfTransactions = tuple.getT3();
                    if (!source.canMakeTransfer(request.getAmount(), numberOfTransactions)) {
                        return Mono.error(new BadRequestException("La cuenta no tiene suficiente saldo para hacer la transferencia"));
                    }
                    double transferAmount = request.getAmount() - source.calculateTransactionFee(numberOfTransactions);
                    source.setBalance(source.getBalance() - transferAmount);
                    destination.setBalance(destination.getBalance() + transferAmount);
                    return Mono.zip(accountRepository.save(source), accountRepository.save(destination));
                }).then();
    }

    @Override
    public TransactionRepository getTransactionRepository() {
        return this.transactionRepository;
    }
}
