package dao;

import database.DatabaseConnection;
import model.User;

import java.sql.*;

public class UserDAO {

    public User login(String username, String password) {

    	System.out.println("Trying username: [" + username + "]");
	    System.out.println("Trying password: [" + password + "]");
	    
        String sql =
                "SELECT * FROM users " +
                "WHERE username = ? " +
                "AND password = ? " +
                "AND status = 'ACTIVE'";

        try (
            // Open connection to MySQL.
            Connection connection = DatabaseConnection.getConnection();

            // Prepare the SQL statement.
            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            // Put the username into the first ?
            statement.setString(1, username);

            // Put the password into the second ?
            statement.setString(2, password);

            // Execute SELECT statement.
            ResultSet result = statement.executeQuery();

            // If a matching user was found...
            if (result.next()) {

                // Create a User object using the database data.
                return new User(
                        result.getInt("user_id"),
                        result.getString("username"),
                        result.getString("password"),
                        result.getString("role"),
                        result.getString("status")
                );
            }

        } catch (Exception e) {

            // Print any database error in Eclipse console.
            e.printStackTrace();
        }

        // Return null if login failed.
        return null;
    }


    public boolean usernameExists(String username) {

        String sql =
                "SELECT user_id FROM users " +
                "WHERE username = ?";

        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            // Put the entered username into the ?
            statement.setString(1, username);

            // Execute the SELECT statement.
            ResultSet result = statement.executeQuery();

            // If a row exists, the username is already registered.
            return result.next();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }


    public int createUser(User user) {

        String sql =
                "INSERT INTO users " +
                "(username, password, role, status) " +
                "VALUES (?, ?, ?, ?)";

        try (
            Connection connection = DatabaseConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(
                            sql,
                            Statement.RETURN_GENERATED_KEYS
                    )
        ) {

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getRole());
            statement.setString(4, user.getStatus());

       
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {

                ResultSet generatedKeys =
                        statement.getGeneratedKeys();

                if (generatedKeys.next()) {

                    return generatedKeys.getInt(1);
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return -1;
    }
}