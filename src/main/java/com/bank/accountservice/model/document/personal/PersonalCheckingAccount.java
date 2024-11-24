package com.bank.accountservice.model.document.personal;

import com.bank.accountservice.model.document.Account;
import com.bank.accountservice.model.document.MaintenanceFeeApplicable;
import com.bank.accountservice.model.enums.AccountType;
import com.bank.accountservice.model.enums.ClientType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonalCheckingAccount extends Account implements MaintenanceFeeApplicable {
    private double maintenanceFee;

    public PersonalCheckingAccount() {
        super(AccountType.CHECKING_ACCOUNT, ClientType.PERSONAL);
    }

    @Override
    public double getMaintenanceFee() {
        return maintenanceFee;
    }

    @Override
    public void setMaintenanceFee(double maintenanceFee) {
        this.maintenanceFee = maintenanceFee;
    }

    @Override
    public boolean hasMaintenanceFee() {
        return true;
    }
}
