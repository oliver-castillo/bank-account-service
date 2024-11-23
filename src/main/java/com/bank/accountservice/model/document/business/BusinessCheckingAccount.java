package com.bank.accountservice.model.document.business;

import com.bank.accountservice.model.document.MaintenanceFeeApplicable;
import com.bank.accountservice.model.enums.ClientType;

public class BusinessCheckingAccount extends BusinessAccount implements MaintenanceFeeApplicable {
    private double maintenanceFee;

    public BusinessCheckingAccount() {
        super(ClientType.BUSINESS);
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
    protected boolean hasMaintenanceFee() {
        return true;
    }
}
