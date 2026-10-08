package dao;

import database.DatabaseConnection;
import model.CreditHistory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

public class CreditHistoryDAO {

    public boolean addHistory(
            CreditHistory history) {

        String sql =
                "INSERT INTO credit_history " +
                "(customer_id, loan_id, payment_status, " +
                "missed_payments, late_payments) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (
            Connection connection =
                    DatabaseConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    history.getCustomerId()
            );

            if (history.getLoanId() == null) {
                statement.setNull(
                        2,
                        java.sql.Types.INTEGER
                );
            } else {
                statement.setInt(
                        2,
                        history.getLoanId()
                );
            }

            statement.setString(
                    3,
                    history.getPaymentStatus()
            );

            statement.setInt(
                    4,
                    history.getMissedPayments()
            );

            statement.setInt(
                    5,
                    history.getLatePayments()
            );

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    
    public List<CreditHistory> getAllHistory() {

        List<CreditHistory> historyList =
                new ArrayList<>();

        String sql =
                "SELECT * FROM credit_history " +
                "ORDER BY credit_history_id DESC";

        try (
            Connection connection =
                    DatabaseConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            ResultSet result =
                    statement.executeQuery()
        ) {

            while (result.next()) {

                Integer loanId =
                        result.getObject("loan_id") == null
                        ? null
                        : result.getInt("loan_id");

                historyList.add(
                        new CreditHistory(
                                result.getInt(
                                        "credit_history_id"
                                ),
                                result.getInt(
                                        "customer_id"
                                ),
                                loanId,
                                result.getString(
                                        "payment_status"
                                ),
                                result.getInt(
                                        "missed_payments"
                                ),
                                result.getInt(
                                        "late_payments"
                                )
                        )
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return historyList;
    }
}