package com.bank.accountservice.controller;

import com.bank.accountservice.model.dto.request.PersonalCheckingAccountRequest;
import com.bank.accountservice.service.AccountService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "accounts/personal-checking-accounts")
public class PersonalCheckingAccountController extends BaseAccountController<PersonalCheckingAccountRequest> {
    public PersonalCheckingAccountController(AccountService<PersonalCheckingAccountRequest> accountService) {
        super(accountService);
    }
}
