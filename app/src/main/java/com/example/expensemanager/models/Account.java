package com.example.expensemanager.models;

public class Account {
    private String accountName;
    private int accountBalance;

    public Account() {
    }



    public Account( int accountBalance,String accountName) {
        this.accountName = accountName;
        this.accountBalance = accountBalance;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public int getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(int accountBalance) {
        this.accountBalance = accountBalance;
    }

}
