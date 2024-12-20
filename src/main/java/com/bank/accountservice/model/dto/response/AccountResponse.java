package com.bank.accountservice.model.dto.response;

import com.bank.accountservice.model.enums.AccountType;
import com.bank.accountservice.model.enums.ClientType;

import java.time.LocalDate;
import java.util.Set;

public record AccountResponse(
        String id,
        String clientId,
        String accountNumber,
        double balance,
        AccountType accountType,
        ClientType clientType,
        Integer transactionLimit,
        double maintenanceFee,
        Set<LocalDate> allowedTransactionDates,
        Double averageMonthlyMinimumAmount,
        Set<HolderResponse> holders,
        Set<SignatoryResponse> signatories,
        boolean isEnabled,
        int freeTransactions,
        double transactionFee) {

    public record HolderResponse(
            String name,
            String paternalSurname,
            String maternalSurname,
            String dni) {
    }

    public record SignatoryResponse(
            String name,
            String paternalSurname,
            String maternalSurname,
            String dni) {
    }
}
