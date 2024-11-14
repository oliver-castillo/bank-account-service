package com.bank.accountservice.controller;

import com.bank.accountservice.model.dto.request.BusinessCheckingAccountRequest;
import com.bank.accountservice.service.AccountService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "accounts/business-checking-accounts")
public class BusinessCheckingAccountController extends BaseAccountController<BusinessCheckingAccountRequest> {
    public BusinessCheckingAccountController(AccountService<BusinessCheckingAccountRequest> accountService) {
        super(accountService);
    }
}
