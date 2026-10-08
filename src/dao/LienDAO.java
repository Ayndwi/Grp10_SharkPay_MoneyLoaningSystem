package dao;

import database.DatabaseConnection;
import model.Lien;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LienDAO {

    public boolean addLien(Lien lien) {

        String sql =
                "INSERT INTO liens " +
                "(customer_id, loan_id, lien_reason, lien_date, status) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (
            Connection connection =
                    DatabaseConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    lien.getCustomerId()
            );

            statement.setInt(
                    2,
                    lien.getLoanId()
            );

            statement.setString(
                    3,
                    lien.getLienReason()
            );

            statement.setDate(
                    4,
                    java.sql.Date.valueOf(
                            lien.getLienDate()
                    )
            );

            statement.setString(
                    5,
                    lien.getStatus()
            );

            return statement.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    public List<Lien> getAllLiens() {

        List<Lien> liens =
                new ArrayList<>();

        String sql =
                "SELECT * FROM liens " +
                "ORDER BY lien_id DESC";

        try (
            Connection connection =
                    DatabaseConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            ResultSet result =
                    statement.executeQuery()
        ) {

            while (result.next()) {

                liens.add(
                        new Lien(
                                result.getInt("lien_id"),
                                result.getInt("customer_id"),
                                result.getInt("loan_id"),
                                result.getString("lien_reason"),
                                result.getDate("lien_date")
                                        .toLocalDate(),
                                result.getString("status")
                        )
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return liens;
    }
    
    public boolean hasActiveLien(int customerId) {

        String sql =
                "SELECT COUNT(*) FROM liens " +
                "WHERE customer_id = ? " +
                "AND status = 'ACTIVE'";

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
                return result.getInt(1) > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}