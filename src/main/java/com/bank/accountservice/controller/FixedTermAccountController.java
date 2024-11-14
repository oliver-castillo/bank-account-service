package com.bank.accountservice.controller;

import com.bank.accountservice.model.dto.request.FixedTermAccountRequest;
import com.bank.accountservice.service.AccountService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "accounts/fixed-term-accounts")
public class FixedTermAccountController extends BaseAccountController<FixedTermAccountRequest> {
    public FixedTermAccountController(AccountService<FixedTermAccountRequest> accountService) {
        super(accountService);
    }
}
