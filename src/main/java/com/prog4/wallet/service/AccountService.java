package com.prog4.wallet.service;

import com.prog4.wallet.models.Account;
import com.prog4.wallet.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

@AllArgsConstructor
@Service
public class AccountService {

    private AccountRepository accountRepository;

    public void createAccount(Account account) {
        try {
            accountRepository.createAccount(account);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Account> getAllAccount() {
        try {
            return accountRepository.getAllAccount();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Account getAccountById(long accountId) {
        try {
            return accountRepository.getAccountById(accountId);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public BigDecimal getAccountBalance(long accountId) {
        try {
            return accountRepository.getAccountBalance(accountId);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public BigDecimal getAccountCredit(long accountId) {
        try {
            return accountRepository.getAccountCredit(accountId);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return BigDecimal.valueOf(0);
    }

    public BigDecimal getAccountCreditInterest(long accountId) {
        try {
            return accountRepository.getAccountCreditInterest(accountId);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return BigDecimal.valueOf(0);
    }

    public void updateAccountSalary(long accountId, BigDecimal accountSalary) {
        try {
            accountRepository.setAccountSalary(accountId, accountSalary);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateAccountBalance(long accountId, BigDecimal accountBalance) {
        try {
            accountRepository.setAccountBalance(accountId, accountBalance);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateAccountCredit(long accountId, BigDecimal accountCredit) {
        try {
            accountRepository.setAccountCredit(accountId, accountCredit);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateAccountCreditInterest(long accountId, BigDecimal accountCreditInterest) {
        try {
            accountRepository.setAccountCreditInterest(accountId, accountCreditInterest);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateAccountCreditStatus(long accountId, boolean accountCreditStatus) {
        try {
            accountRepository.setAccountCreditStatus(accountId, accountCreditStatus);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteAccount(long accountId) {
        try {
            accountRepository.deleteAccount(accountId);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}