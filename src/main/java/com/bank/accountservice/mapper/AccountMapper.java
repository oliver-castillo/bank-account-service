package com.bank.accountservice.mapper;

import com.bank.accountservice.exception.BadRequestException;
import com.bank.accountservice.model.document.Account;
import com.bank.accountservice.model.document.business.BusinessCheckingAccount;
import com.bank.accountservice.model.document.business.BusinessPymeCheckingAccount;
import com.bank.accountservice.model.document.personal.PersonalCheckingAccount;
import com.bank.accountservice.model.document.personal.PersonalFixedTermAccount;
import com.bank.accountservice.model.document.personal.PersonalSavingsAccount;
import com.bank.accountservice.model.document.personal.PersonalVipSavingsAccount;
import com.bank.accountservice.model.dto.request.account.*;
import com.bank.accountservice.model.dto.response.AccountResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    PersonalSavingsAccount toDocument(PersonalSavingsAccountRequest accountRequest);

    PersonalVipSavingsAccount toDocument(PersonalVipSavingsAccountRequest accountRequest);

    PersonalCheckingAccount toDocument(PersonalCheckingAccountRequest accountRequest);

    PersonalFixedTermAccount toDocument(PersonalFixedTermAccountRequest accountRequest);

    BusinessCheckingAccount toDocument(BusinessCheckingAccountRequest accountRequest);

    BusinessPymeCheckingAccount toDocument(BusinessPymeCheckingAccountRequest accountRequest);

    AccountResponse toResponse(PersonalSavingsAccount account);

    AccountResponse toResponse(PersonalVipSavingsAccount account);

    AccountResponse toResponse(PersonalCheckingAccount account);

    AccountResponse toResponse(PersonalFixedTermAccount account);

    AccountResponse toResponse(BusinessCheckingAccount account);

    AccountResponse toResponse(BusinessPymeCheckingAccount account);

    default AccountResponse toResponse(Account account) {
        if (account instanceof PersonalSavingsAccount object) {
            return toResponse(object);
        } else if (account instanceof PersonalVipSavingsAccount object) {
            return toResponse(object);
        } else if (account instanceof PersonalCheckingAccount object) {
            return toResponse(object);
        } else if (account instanceof PersonalFixedTermAccount object) {
            return toResponse(object);
        } else if (account instanceof BusinessCheckingAccount object) {
            return toResponse(object);
        } else if (account instanceof BusinessPymeCheckingAccount object) {
            return toResponse(object);
        } else {
            throw new BadRequestException("Tipo de cuenta no válido");
        }
    }

    /**
     * Tipos de cuentas
     * 1. Personal - Cuenta de ahorro (Savings Account)
     * 2. Personal - Cuenta corriente (Checking Account)
     * 3. Personal - Cuenta plazo fijo (Fixed Term Account)
     * 4. Personal - Cuenta de ahorro VIP (VIP Savings Account)
     * 5. Empresarial - Cuenta corriente (Checking Account)
     * 6. Empresarial - Cuenta corriente Pyme (Pyme Checking Account)
     */

    default Account toDocument(AccountRequest accountRequest) {
        return switch (accountRequest.getType()) {
            case 1 -> toDocument((PersonalSavingsAccountRequest) accountRequest);
            case 2 -> toDocument((PersonalCheckingAccountRequest) accountRequest);
            case 3 -> toDocument((PersonalFixedTermAccountRequest) accountRequest);
            case 4 -> toDocument((PersonalVipSavingsAccountRequest) accountRequest);
            case 5 -> toDocument((BusinessCheckingAccountRequest) accountRequest);
            case 6 -> toDocument((BusinessPymeCheckingAccountRequest) accountRequest);
            default -> throw new BadRequestException("Tipo de cuenta no válido");
        };
    }
}
