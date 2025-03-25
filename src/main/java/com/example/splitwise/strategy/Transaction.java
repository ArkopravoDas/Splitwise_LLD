package com.example.splitwise.strategy;

import com.example.splitwise.models.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Transaction {
    double amount;
    User paidBy;
    User paidTo;

    public Transaction(double amount, User paidBy, User paidTo) {
        this.amount = amount;
        this.paidBy = paidBy;
        this.paidTo = paidTo;
    }
}
