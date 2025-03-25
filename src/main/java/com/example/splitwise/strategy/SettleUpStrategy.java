package com.example.splitwise.strategy;

import com.example.splitwise.models.Expense;

import java.util.List;

public interface SettleUpStrategy {
    List<Transaction> settleUp(List<Expense> expense);
}
