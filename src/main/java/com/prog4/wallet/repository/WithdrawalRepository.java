package com.prog4.wallet.repository;

import com.prog4.wallet.DatabaseConnection.DatabaseConnection;
import com.prog4.wallet.models.Withdrawal;
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
public class WithdrawalRepository {

    private DatabaseConnection dbConnection;
    private Connection connection = DatabaseConnection.getConnection();

    public Withdrawal mapWhithDrawal(ResultSet resultSet) throws SQLException {
        return new Withdrawal(
                resultSet.getLong("withdrawal_id"),
                resultSet.getLong("account_id"),
                resultSet.getBigDecimal("withdrawal_amount"),
                resultSet.getTimestamp("withdrawal_date")
        );
    }

    public void doWithdrawal(long withdrawalId, BigDecimal withdrawalAmount) throws SQLException {
        String withdrawalSql = "INSERT INTO withdrawal " +
                "(account_id, withdrawal_amount) " +
                "VALUES (?, ?, ?)";
        String accountSql = "UPDATE account SET balance = balance - ? WHERE account_id = ?;";
        try {
            PreparedStatement withdrawStatement = connection.prepareStatement(withdrawalSql);
            withdrawStatement.setLong(1, withdrawalId);
            withdrawStatement.setBigDecimal(2, withdrawalAmount);
            withdrawStatement.executeUpdate();

            PreparedStatement accountStatement = connection.prepareStatement(accountSql);
            accountStatement.setBigDecimal(1, withdrawalAmount);
            accountStatement.setLong(2, withdrawalId);
            accountStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Withdrawal> getWithdrawalHistory() throws SQLException {
        List<Withdrawal> withdrawalList = new ArrayList<>();
        String sql = "SELECT * FROM withdrawal;";
        try {
            ResultSet resultSet = connection.createStatement().executeQuery(sql);
            while (resultSet.next()) {
                withdrawalList.add(mapWhithDrawal(resultSet));
            }
            return withdrawalList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Withdrawal getWithdrawalByAccountId(long accountId) throws SQLException {
        String sql = "SELECT * FROM withdrawal WHERE account_id = ?;";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, accountId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapWhithDrawal(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}