package com.bank.accountservice.mapper;

import com.bank.accountservice.model.document.BusinessCheckingAccountDocument;
import com.bank.accountservice.model.document.FixedTermAccountDocument;
import com.bank.accountservice.model.document.PersonalCheckingAccountDocument;
import com.bank.accountservice.model.document.SavingAccountDocument;
import com.bank.accountservice.model.dto.request.BusinessCheckingAccountRequest;
import com.bank.accountservice.model.dto.request.FixedTermAccountRequest;
import com.bank.accountservice.model.dto.request.PersonalCheckingAccountRequest;
import com.bank.accountservice.model.dto.request.SavingAccountRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    @Mapping(target = "type", constant = "SAVING_ACCOUNT")
    SavingAccountDocument toDocument(SavingAccountRequest request);

    @Mapping(target = "type", constant = "FIXED_TERM_ACCOUNT")
    FixedTermAccountDocument toDocument(FixedTermAccountRequest request);

    @Mapping(target = "type", constant = "CHECKING_ACCOUNT")
    BusinessCheckingAccountDocument toDocument(BusinessCheckingAccountRequest request);

    @Mapping(target = "type", constant = "CHECKING_ACCOUNT")
    PersonalCheckingAccountDocument toDocument(PersonalCheckingAccountRequest request);
}
