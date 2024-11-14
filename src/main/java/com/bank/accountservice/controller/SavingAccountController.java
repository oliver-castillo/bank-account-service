package com.bank.accountservice.controller;

import com.bank.accountservice.model.dto.request.SavingAccountRequest;
import com.bank.accountservice.service.AccountService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "accounts/saving-accounts")
public class SavingAccountController extends BaseAccountController<SavingAccountRequest> {
    public SavingAccountController(AccountService<SavingAccountRequest> accountService) {
        super(accountService);
    }
}
