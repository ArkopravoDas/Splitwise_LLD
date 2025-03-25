package com.example.splitwise.services;

import com.example.splitwise.dtos.CreateExpenseDto;
import com.example.splitwise.models.Expense;
import com.example.splitwise.models.Group;
import com.example.splitwise.models.User;
import com.example.splitwise.models.UserExpense;
import com.example.splitwise.repository.ExpenseRepository;
import com.example.splitwise.repository.GroupRepository;
import com.example.splitwise.repository.UserExpenseRepository;
import com.example.splitwise.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ExpenseService {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final ExpenseRepository expenseRepository;
    private final UserExpenseRepository userExpenseRepository;

    public ExpenseService(UserRepository userRepository, GroupRepository groupRepository, ExpenseRepository expenseRepository, UserExpenseRepository userExpenseRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.expenseRepository = expenseRepository;
        this.userExpenseRepository = userExpenseRepository;
    }

    @Transactional
    public Expense createExpense(CreateExpenseDto createExpenseDto) {

        // Group to which the expense is attached
        Optional<Group> groupOptional = groupRepository.findById(createExpenseDto.getGroupId());

        // Create a corresponding expense object


        // Finds out who all paid
        List<UserExpense> paidByUserExpenses = new ArrayList<>();
        for(Map.Entry<Long, Integer> entry : createExpenseDto.getPaidBy().entrySet()) {
            Long userId = entry.getKey();
            int amount = entry.getValue();
            User user = userRepository.findById(userId).get();
            UserExpense userExpense = new UserExpense(user, amount);
            paidByUserExpenses.add(userExpense);
        }

        List<UserExpense> paidByUsers = userExpenseRepository.saveAll(paidByUserExpenses);

        // Finds out who all owed
        List<UserExpense> owedByUserExpenses = new ArrayList<>();
        for(Map.Entry<Long, Integer> entry : createExpenseDto.getOwedBy().entrySet()) {
            Long userId = entry.getKey();
            int amount = entry.getValue();
            User user = userRepository.findById(userId).get();
            UserExpense userExpense = new UserExpense(user, -amount);
            owedByUserExpenses.add(userExpense);
        }

        List<UserExpense> owedByUsers = userExpenseRepository.saveAll(owedByUserExpenses);

        // Add entries to the DB.
        Expense expense =  Expense.builder()
                .name(createExpenseDto.getName())
                .group(groupOptional.get())
                .amount(createExpenseDto.getAmount())
                .owedBy(owedByUsers)
                .paidBy(paidByUsers)
                .build();

        return expenseRepository.save(expense);
    }
}
