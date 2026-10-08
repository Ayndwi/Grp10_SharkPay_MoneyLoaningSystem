package dao;

import database.DatabaseConnection;
import model.Payment;

import java.sql.*;

public class PaymentDAO {

    public boolean addPayment(Payment payment) {

        String sql =
                "INSERT INTO payments " +
                "(loan_id, payment_amount, payment_date, payment_method) " +
                "VALUES (?, ?, ?, ?)";

        try (
            Connection connection =
                    DatabaseConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setInt(1, payment.getLoanId());
            statement.setDouble(
                    2,
                    payment.getPaymentAmount()
            );

            statement.setDate(
                    3,
                    java.sql.Date.valueOf(
                            payment.getPaymentDate()
                    )
            );

            statement.setString(
                    4,
                    payment.getPaymentMethod()
            );

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    
    public int countPaymentsByLoanId(int loanId) {

        String sql =
                "SELECT COUNT(*) FROM payments " +
                "WHERE loan_id = ?";

        try (
            Connection connection =
                    DatabaseConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setInt(1, loanId);

            ResultSet result =
                    statement.executeQuery();

            if (result.next()) {
                return result.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}