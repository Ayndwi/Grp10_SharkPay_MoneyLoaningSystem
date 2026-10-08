package dao;

import database.DatabaseConnection;

import model.Loan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

public class LoanDAO {

    public boolean createLoan(Loan loan) {

        String sql =
                "INSERT INTO loans " +
                "(customer_id, loan_amount, interest_rate, " +
                "term_months, total_amount, remaining_balance, " +
                "application_date, due_date, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (
            Connection connection =
                    DatabaseConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setInt(1, loan.getCustomerId());
            statement.setDouble(2, loan.getLoanAmount());
            statement.setDouble(3, loan.getInterestRate());
            statement.setInt(4, loan.getTermMonths());
            statement.setDouble(5, loan.getTotalAmount());
            statement.setDouble(6, loan.getRemainingBalance());
            statement.setDate(
                    7,
                    java.sql.Date.valueOf(
                            loan.getApplicationDate()
                    )
            );
            statement.setDate(
                    8,
                    java.sql.Date.valueOf(
                            loan.getDueDate()
                    )
            );
            statement.setString(9, loan.getStatus());

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    

    public Loan getCurrentLoan(int customerId) {

        String sql =
                "SELECT * FROM loans " +
                "WHERE customer_id = ? " +
                "AND status IN ('APPROVED', 'OVERDUE') " +
                "ORDER BY loan_id DESC LIMIT 1";

        try (
            Connection connection =
                    DatabaseConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setInt(1, customerId);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return resultToLoan(result);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean updateBalance(
            int loanId,
            double balance,
            String status) {

        String sql =
                "UPDATE loans " +
                "SET remaining_balance = ?, status = ? " +
                "WHERE loan_id = ?";

        try (
            Connection connection =
                    DatabaseConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setDouble(1, balance);
            statement.setString(2, status);
            statement.setInt(3, loanId);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private Loan resultToLoan(ResultSet result)
            throws Exception {

        return new Loan(
                result.getInt("loan_id"),
                result.getInt("customer_id"),
                result.getDouble("loan_amount"),
                result.getDouble("interest_rate"),
                result.getInt("term_months"),
                result.getDouble("total_amount"),
                result.getDouble("remaining_balance"),
                result.getDate("application_date")
                        .toLocalDate(),
                result.getDate("due_date")
                        .toLocalDate(),
                result.getString("status")
        );
    }
    
    public List<Loan> getLoansByCustomerId(
            int customerId) {

        List<Loan> loans = new ArrayList<>();

        String sql =
                "SELECT * FROM loans " +
                "WHERE customer_id = ? " +
                "ORDER BY application_date DESC";

        try (
            Connection connection =
                    DatabaseConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setInt(1, customerId);

            ResultSet result =
                    statement.executeQuery();

            while (result.next()) {

                loans.add(
                        resultToLoan(result)
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return loans;
    }
    
    public int countPaidLoans(int customerId) {

        String sql =
                "SELECT COUNT(*) FROM loans " +
                "WHERE customer_id = ? " +
                "AND status = 'PAID'";

        try (
            Connection connection =
                    DatabaseConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setInt(1, customerId);

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

    
    public List<Loan> getAllLoans() {

        List<Loan> loans =
                new ArrayList<>();

        String sql =
                "SELECT * FROM loans " +
                "ORDER BY loan_id DESC";

        try (
            Connection connection =
                    DatabaseConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            ResultSet result =
                    statement.executeQuery()
        ) {

            while (result.next()) {

                loans.add(
                        resultToLoan(result)
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return loans;
    }
}