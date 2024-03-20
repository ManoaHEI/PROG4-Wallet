package com.prog4.wallet.repository;

import com.prog4.wallet.DatabaseConnection.DatabaseConnection;
import com.prog4.wallet.models.Account;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Repository
public class AccountRepository {

    private DatabaseConnection dbConnection;
    private Connection connection = DatabaseConnection.getConnection();

    public Account mapAccount(ResultSet resultSet) throws SQLException {
        return new Account(
                resultSet.getLong("account_id"),
                resultSet.getString("name"),
                resultSet.getString("firstname"),
                resultSet.getDate("birthdate"),
                resultSet.getBigDecimal("salary"),
                resultSet.getBigDecimal("balance"),
                resultSet.getBigDecimal("credit"),
                resultSet.getDouble("credit_interest"),
                resultSet.getBoolean("can_take_credit")
        );
    }

    public ResultSet getResultSet(long accountId, String sql) {
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, accountId);
            return statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Account> getAllAccount() throws SQLException {
        List<Account> accountList = new ArrayList<>();
        String sql = "SELECT * FROM account;";
        try {
            ResultSet resultSet = connection.createStatement().executeQuery(sql);
            while (resultSet.next()) {
                accountList.add(mapAccount(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accountList;
    }

    public Account getAccountById(long accountId) throws SQLException {
        String sql = "SELECT * FROM account WHERE account_id = ?;";
        try {
            ResultSet resultSet = getResultSet(accountId, sql);
            if (resultSet.next()) {
                return mapAccount(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public BigDecimal getAccountBalance(long accountId) throws SQLException {
        String sql = "SELECT balance FROM account WHERE account_id = ?;";
        try {
            ResultSet resultSet = getResultSet(accountId, sql);
            if (resultSet.next()) {
                return resultSet.getBigDecimal("balance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public BigDecimal getAccountSalary(long accountId) throws SQLException {
        String sql = "SELECT salary FROM account WHERE account_id = ?;";
        try {
            ResultSet resultSet = getResultSet(accountId, sql);
            if (resultSet.next()) {
                return resultSet.getBigDecimal("salary");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public BigDecimal getAccountCredit(long accountId) throws SQLException {
        String sql = "SELECT credit FROM account WHERE account_id = ?;";
        try {
            ResultSet resultSet = getResultSet(accountId, sql);
            if (resultSet.next()) {
                return resultSet.getBigDecimal("credit");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public BigDecimal getAccountCreditInterest(long accountId) throws SQLException {
        String sql = "SELECT credit_interest FROM account WHERE account_id = ?;";
        try {
            ResultSet resultSet = getResultSet(accountId, sql);
            if (resultSet.next()) {
                return resultSet.getBigDecimal("credit_interest");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean getCreditStatus(long accountId) throws SQLException {
        String sql = "SELECT can_take_credit FROM account WHERE account_id = ?;";
        try {
            ResultSet resultSet = getResultSet(accountId, sql);
            if (resultSet.next()) {
                return resultSet.getBoolean("can_take_credit");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void createAccount(Account account) throws SQLException {
        String sql = "INSERT INTO account " +
                "(name, firstname, birthdate, salary, balance) VALUES " +
                "(?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, account.getFirstname());
            statement.setString(2, account.getFirstname());
            statement.setDate(2, account.getBirthdate());
            statement.setBigDecimal(4, account.getSalary());
            statement.setBigDecimal(5, account.getBalance());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setAccountSalary(long accountId, BigDecimal accountSalary) throws SQLException {
        String sql = "UPDATE account SET salary = ? WHERE account_id = ?;";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setBigDecimal(1, accountSalary);
            statement.setLong(2, accountId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setAccountBalance(long accountId, BigDecimal accountBalance) throws SQLException {
        String sql = "UPDATE account SET balance = ? WHERE account_id = ?;";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setBigDecimal(1, accountBalance);
            statement.setLong(2, accountId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setAccountCredit(long accountId, BigDecimal accountCredit) throws SQLException {
        String sql = "UPDATE account SET credit = ? WHERE account_id = ?;";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setBigDecimal(1, accountCredit);
            statement.setLong(2, accountId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setAccountCreditInterest(long accountId, BigDecimal accountCreditInterest) throws SQLException {
        String sql = "UPDATE account SET credit_interest = ? WHERE account_id = ?;";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setBigDecimal(1, accountCreditInterest);
            statement.setLong(2, accountId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setAccountCreditStatus(long accountId, boolean accountCreditStatus) throws SQLException {
        String sql = "UPDATE account SET can_take_credit = ? WHERE account_id = ?;";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setBoolean(1, accountCreditStatus);
            statement.setLong(2, accountId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAccount(long accountId) throws SQLException {
        String sql = "DELETE FROM account WHERE account_id = ?;";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, accountId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}