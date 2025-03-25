package com.example.splitwise.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_expense")
@AllArgsConstructor
@NoArgsConstructor
public class UserExpense extends BaseModel implements  Comparable<UserExpense> {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    int amount;

    @Override
    public int compareTo(UserExpense userExpense) {
        return Integer.compare(amount, userExpense.amount);
    }
}
