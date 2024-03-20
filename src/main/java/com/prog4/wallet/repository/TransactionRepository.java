package com.prog4.wallet.repository;

import com.prog4.wallet.DatabaseConnection.DatabaseConnection;
import com.prog4.wallet.models.Transaction;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.*;

@AllArgsConstructor
@Repository
public class TransactionRepository {

    private DatabaseConnection dbConnection;
    private Connection connection = DatabaseConnection.getConnection();

    public Transaction mapTransaction(ResultSet resultSet) throws SQLException {
        return new Transaction(
                resultSet.getLong("transaction_id"),
                resultSet.getString("label"),
                resultSet.getTimestamp("transaction_date"),
                resultSet.getBigDecimal("amount"),
                resultSet.getString("transaction_type"),
                resultSet.getBigDecimal("balance"),
                resultSet.getLong("account_id")
        );
    }

    public ResultSet getResultSet(long transactionId, String sql) {
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, transactionId);
            return statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void doTransaction(Transaction transaction) throws SQLException {
        String sql = "INSERT INTO transaction " +
                "(label, transaction_date, amount, transaction_type, balance, account_id) " +
                "VALUES (?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, transaction.getLabel());
            statement.setTimestamp(2, transaction.getTransactionDate());
            statement.setBigDecimal(3, transaction.getAmount());
            statement.setString(4, transaction.getTransactionType());
            statement.setBigDecimal(5, transaction.getBalance());
            statement.setLong(6, transaction.getAccountId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getTransactionLabel(long transactionId) {
        String sql = "SELECT label FROM transaction WHERE transaction_id = ?;";
        try {
            ResultSet resultSet = getResultSet(transactionId, sql);
            if (resultSet.next()) {
                return resultSet.getString("label");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Timestamp getTransactionDate(long transactionId) {
        String sql = "SELECT transaction_date FROM transaction WHERE transaction_id = ?;";
        try {
            ResultSet resultSet = getResultSet(transactionId, sql);
            if (resultSet.next()) {
                return resultSet.getTimestamp("transaction_date");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public BigDecimal getTransactionAmount(long transactionId) {
        String sql = "SELECT amount FROM transaction WHERE transaction_id = ?;";
        try {
            ResultSet resultSet = getResultSet(transactionId, sql);
            if (resultSet.next()) {
                return resultSet.getBigDecimal("amount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getTransactionType(long transactionId) {
        String sql = "SELECT transaction_type FROM transaction WHERE transaction_id = ?;";
        try {
            ResultSet resultSet = getResultSet(transactionId, sql);
            if (resultSet.next()) {
                return resultSet.getString("transaction_type");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public BigDecimal getBalance(long transactionId) {
        String sql = "SELECT balance FROM transaction WHERE transaction_id = ?;";
        try {
            ResultSet resultSet = getResultSet(transactionId, sql);
            if (resultSet.next()) {
                return resultSet.getBigDecimal("balance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Long getTransactionAccountId(long transactionId) {
        String sql = "SELECT account_date FROM transaction WHERE transaction_id = ?;";
        try {
            ResultSet resultSet = getResultSet(transactionId, sql);
            if (resultSet.next()) {
                return resultSet.getLong("account_date");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}