package com.bank.accountservice.model.document.personal;

import com.bank.accountservice.model.document.Account;
import com.bank.accountservice.model.document.AverageMonthlyMinimumAmount;
import com.bank.accountservice.model.enums.AccountType;
import com.bank.accountservice.model.enums.ClientType;

public class PersonalVipSavingsAccount extends Account implements AverageMonthlyMinimumAmount {
    private double averageMonthlyMinimumAmount;

    public PersonalVipSavingsAccount() {
        super(AccountType.SAVINGS_ACCOUNT, ClientType.PERSONAL_VIP);
    }

    @Override
    public double getAverageMonthlyMinimumAmount() {
        return averageMonthlyMinimumAmount;
    }

    @Override
    public void setAverageMonthlyMinimumAmount(double averageMonthlyMinimumAmount) {
        this.averageMonthlyMinimumAmount = averageMonthlyMinimumAmount;
    }

    @Override
    public boolean requiresCreditCard() {
        return true;
    }

    @Override
    public boolean requiresAverageMonthlyMinimumAmount() {
        return true;
    }
}
