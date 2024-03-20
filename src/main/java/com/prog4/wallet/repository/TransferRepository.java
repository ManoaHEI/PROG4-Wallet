package com.prog4.wallet.repository;

import com.prog4.wallet.DatabaseConnection.DatabaseConnection;
import com.prog4.wallet.models.Transfer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.*;

@AllArgsConstructor
@Repository
public class TransferRepository {

    private DatabaseConnection dbConnection;
    private Connection connection = DatabaseConnection.getConnection();

    public Transfer mapTransfer(ResultSet resultSet) throws SQLException {
        return new Transfer(
                resultSet.getLong("transfer_id"),
                resultSet.getLong("account_id"),
                resultSet.getLong("receiver_id"),
                resultSet.getBoolean("have_same_bank"),
                resultSet.getString("bank_name"),
                resultSet.getBigDecimal("transfer_amount"),
                resultSet.getString("transfer_category"),
                resultSet.getTimestamp("effective_date"),
                resultSet.getTimestamp("registration_date"),
                resultSet.getString("transfer_ref"),
                resultSet.getBoolean("is_canceled")
        );
    }

    public ResultSet getResultSet(String transferRef, String sql) {
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, transferRef);
            return statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet getResultSet(long transferId, String sql) {
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, transferId);
            return statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void transfer(Transfer transfer) throws SQLException {
        String sql = "INSERT INTO transfer " +
                "(account_id, receiver_id, have_same_bank, bank_name, transfer_amount, transfer_category, effective_date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, transfer.getAccountId());
            statement.setLong(2, transfer.getReceiverId());
            statement.setBoolean(3, transfer.isHaveSameBank());
            statement.setString(4, transfer.getBankName());
            statement.setBigDecimal(5, transfer.getTransferAmount());
            statement.setString(6, transfer.getTransferCategory());
            statement.setTimestamp(7, transfer.getEffectiveDate());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public long getTransferAccountId(String transferRef) throws SQLException {
        String sql = "SELECT account_id FROM transfer WHERE transfer_ref = ?;";
        try {
            ResultSet resultSet = getResultSet(transferRef, sql);
            if (resultSet.next()) {
                return resultSet.getLong("account_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public long getTransferReceiverId(String transferRef) throws SQLException {
        String sql = "SELECT receiver_id FROM transfer WHERE transfer_ref = ?;";
        try {
            ResultSet resultSet = getResultSet(transferRef, sql);
            if (resultSet.next()) {
                return resultSet.getLong("receiver_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean isSameBank(String transferRef) throws SQLException {
        String sql = "SELECT have_same_bank FROM transfer WHERE transfer_ref = ?;";
        try {
            ResultSet resultSet = getResultSet(transferRef, sql);
            if (resultSet.next()) {
                return resultSet.getBoolean("have_same_bank");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getBankName(String transferRef) throws SQLException {
        String sql = "SELECT bank_name FROM transfer WHERE transfer_ref = ?;";
        try {
            ResultSet resultSet = getResultSet(transferRef, sql);
            if (resultSet.next()) {
                return resultSet.getString("bank_name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public BigDecimal getTransferAmount(String transferRef) throws SQLException {
        String sql = "SELECT transfer_amount FROM transfer WHERE transfer_ref = ?;";
        try {
            ResultSet resultSet = getResultSet(transferRef, sql);
            if (resultSet.next()) {
                return resultSet.getBigDecimal("transfer_amount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getTransferCategory(String transferRef) throws SQLException {
        String sql = "SELECT transfer_category FROM transfer WHERE transfer_ref = ?;";
        try {
            ResultSet resultSet = getResultSet(transferRef, sql);
            if (resultSet.next()) {
                return resultSet.getString("transfer_category");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Timestamp getEffectiveDate(String transferRef) throws SQLException {
        String sql = "SELECT effective_date FROM transfer WHERE transfer_ref = ?;";
        try {
            ResultSet resultSet = getResultSet(transferRef, sql);
            if (resultSet.next()) {
                return resultSet.getTimestamp("effective_date");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Timestamp getRegistrationDate(String transferRef) throws SQLException {
        String sql = "SELECT registration_date FROM transfer WHERE transfer_ref = ?;";
        try {
            ResultSet resultSet = getResultSet(transferRef, sql);
            if (resultSet.next()) {
                return resultSet.getTimestamp("registration_date");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getTransferRef(long transferId) throws SQLException {
        String sql = "SELECT transfer_ref FROM transfer WHERE transfer_id = ?;";
        try {
            ResultSet resultSet = getResultSet(transferId, sql);
            if (resultSet.next()) {
                return resultSet.getString("transfer_ref");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isCanceled(String transferRef) throws SQLException {
        String sql = "SELECT is_canceled FROM transfer WHERE transfer_ref = ?;";
        try {
            ResultSet resultSet = getResultSet(transferRef, sql);
            if (resultSet.next()) {
                return resultSet.getBoolean("is_canceled");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void updateTransferStatus(String transferRef, boolean isCanceled) throws SQLException {
        String sql = "UPDATE transfer SET is_canceled = ? WHERE transfer_ref = ?;";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setBoolean(1, isCanceled);
            statement.setString(2, transferRef);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}