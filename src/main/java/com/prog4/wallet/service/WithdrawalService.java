package com.prog4.wallet.service;

import com.prog4.wallet.models.Withdrawal;
import com.prog4.wallet.repository.AccountRepository;
import com.prog4.wallet.repository.WithdrawalRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

@AllArgsConstructor
@Service
public class WithdrawalService {

    private WithdrawalRepository withdrawalRepository;
    private AccountRepository accountRepository;

    @Transactional
    public String doWithdraw(long id, BigDecimal withdrawalAmount) throws SQLException {

        BigDecimal balance = accountRepository.getAccountBalance(id);
        boolean creditStatus = accountRepository.getCreditStatus(id);
        BigDecimal salary = accountRepository.getAccountSalary(id);
        BigDecimal credit = BigDecimal.valueOf(0);

        try {
            if (creditStatus) {
                if (balance + (salary / 3) >= withdrawalAmount && withdrawalAmount != 0) {
                    withdrawalRepository.doWithdrawal(id, withdrawalAmount);
                    balance = balance - withdrawalAmount;

                    if (balance < 0) {
                        BigDecimal newCredit = Math.abs(balance);
                        credit = accountRepository.getAccountCredit(id);
                        credit = credit + newCredit;
                        accountRepository.setAccountCredit(id, credit);
                    }
                    return "Withdraw done";
                } else if (balance < withdrawalAmount && 0 != withdrawalAmount) {
                    return "Insufficient balance" ;
                } else {
                    return "Invalid amount";
                }
            } else {
                if (balance >= withdrawalAmount && 0 != withdrawalAmount){
                    withdrawalRepository.doWithdrawal(id, withdrawalAmount);
                    return "Withdraw of " + withdrawalAmount + " successfully completed ! ";
                } else if (
                        (balance < withdrawalAmount)
                                && ((balance + balance/3) < withdrawalAmount)
                                && 0 != withdrawalAmount
                ) {
                    return "Insufficient balance and credit is not available";
                } else if (balance < withdrawalAmount && 0 != withdrawalAmount) {
                    return "Insufficient balance";
                } else {
                    return "Invalid amount";
                }
            }
        }

    }

    public List<Withdrawal> getWithdrawalHistory() {
        try {
            return withdrawalRepository.getWithdrawalHistory();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Withdrawal getWithdrawalById(long accountId) {
        try {
            return withdrawalRepository.getWithdrawalByAccountId(accountId);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}