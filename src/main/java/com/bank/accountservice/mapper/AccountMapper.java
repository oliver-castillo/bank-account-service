package com.bank.accountservice.mapper;

import com.bank.accountservice.model.document.Account;
import com.bank.accountservice.model.document.business.BusinessCheckingAccount;
import com.bank.accountservice.model.document.business.BusinessPymeCheckingAccount;
import com.bank.accountservice.model.document.personal.PersonalCheckingAccount;
import com.bank.accountservice.model.document.personal.PersonalFixedTermAccount;
import com.bank.accountservice.model.document.personal.PersonalSavingsAccount;
import com.bank.accountservice.model.document.personal.PersonalVipSavingsAccount;
import com.bank.accountservice.model.dto.request.*;
import com.bank.accountservice.model.enums.AccountType;
import com.bank.accountservice.model.enums.ClientType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    PersonalSavingsAccount toDocument(PersonalSavingsAccountRequest accountRequest);

    PersonalVipSavingsAccount toDocument(PersonalVipSavingsAccountRequest accountRequest);

    PersonalCheckingAccount toDocument(PersonalCheckingAccountRequest accountRequest);

    PersonalFixedTermAccount toDocument(PersonalFixedTermAccountRequest accountRequest);

    BusinessCheckingAccount toDocument(BusinessCheckingAccountRequest accountRequest);

    BusinessPymeCheckingAccount toDocument(BusinessPymeCheckingAccountRequest accountRequest);

    default Account toDocument(AccountType accountType, ClientType clientType, AccountRequest accountRequest) {
        return switch (clientType) {
            case PERSONAL -> switch (accountType) {
                case SAVINGS_ACCOUNT -> toDocument((PersonalSavingsAccountRequest) accountRequest);
                case CHECKING_ACCOUNT -> toDocument((PersonalCheckingAccountRequest) accountRequest);
                case FIXED_TERM_ACCOUNT -> toDocument((PersonalFixedTermAccountRequest) accountRequest);
            };
            case PERSONAL_VIP -> toDocument((PersonalVipSavingsAccountRequest) accountRequest);
            case BUSINESS -> toDocument((BusinessCheckingAccountRequest) accountRequest);
            case BUSINESS_PYME -> toDocument((BusinessPymeCheckingAccountRequest) accountRequest);
        };
    }
}
