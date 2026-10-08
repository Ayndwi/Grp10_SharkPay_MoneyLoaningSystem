package dao;

import database.DatabaseConnection;
import model.Customer;

import java.sql.*;

import java.util.*;

public class CustomerDAO {

    public Customer getCustomerByUserId(int userId) {

        String sql =
                "SELECT * FROM customers WHERE user_id = ?";

        try (
            Connection connection =
                    DatabaseConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setInt(1, userId);

            ResultSet result = statement.executeQuery();

            if (result.next()) {

                return new Customer(
                        result.getInt("customer_id"),
                        result.getInt("user_id"),
                        result.getString("first_name"),
                        result.getString("last_name"),
                        result.getString("email"),
                        result.getString("phone"),
                        result.getString("address"),
                        result.getDouble("salary"),
                        result.getDouble("credit_limit")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    
	 // =========================================================
	 // CREATE NEW CUSTOMER
	 // =========================================================
	
	 public boolean createCustomer(Customer customer) {
	
	     String sql =
	             "INSERT INTO customers " +
	             "(user_id, first_name, last_name, email, phone, " +
	             "address, salary, credit_limit) " +
	             "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	
	     try (
	         Connection connection = DatabaseConnection.getConnection();
	
	         PreparedStatement statement =
	                 connection.prepareStatement(sql)
	     ) {
	
	         // Connect this customer to the User account.
	         statement.setInt(1, customer.getUserId());
	
	         // Customer personal information.
	         statement.setString(2, customer.getFirstName());
	         statement.setString(3, customer.getLastName());
	         statement.setString(4, customer.getEmail());
	         statement.setString(5, customer.getPhone());
	         statement.setString(6, customer.getAddress());
	
	         // Financial information.
	         statement.setDouble(7, customer.getSalary());
	         statement.setDouble(8, customer.getCreditLimit());
	
	         // Execute INSERT.
	         return statement.executeUpdate() > 0;
	
	     } catch (Exception e) {
	
	         e.printStackTrace();
	     }
	
	     return false;
	 }
	
	 public boolean updateCreditLimit(
		        int customerId,
		        double newCreditLimit) {

		    String sql =
		            "UPDATE customers " +
		            "SET credit_limit = ? " +
		            "WHERE customer_id = ?";

		    try (
		        Connection connection =
		                DatabaseConnection.getConnection();

		        PreparedStatement statement =
		                connection.prepareStatement(sql)
		    ) {

		        statement.setDouble(
		                1,
		                newCreditLimit
		        );

		        statement.setInt(
		                2,
		                customerId
		        );

		        return statement.executeUpdate() > 0;

		    } catch (Exception e) {
		        e.printStackTrace();
		    }

		    return false;
		}
    
    public List<Customer> getAllCustomers() {

        List<Customer> customers =
                new ArrayList<>();

        String sql =
                "SELECT * FROM customers " +
                "ORDER BY customer_id";

        try (
            Connection connection =
                    DatabaseConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            ResultSet result =
                    statement.executeQuery()
        ) {

            while (result.next()) {

                customers.add(
                        new Customer(
                                result.getInt("customer_id"),
                                result.getInt("user_id"),
                                result.getString("first_name"),
                                result.getString("last_name"),
                                result.getString("email"),
                                result.getString("phone"),
                                result.getString("address"),
                                result.getDouble("salary"),
                                result.getDouble("credit_limit")
                        )
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return customers;
    }
    
    
    public Customer getCustomerById(int customerId) {

        String sql =
                "SELECT * FROM customers " +
                "WHERE customer_id = ?";

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

                return new Customer(
                        result.getInt("customer_id"),
                        result.getInt("user_id"),
                        result.getString("first_name"),
                        result.getString("last_name"),
                        result.getString("email"),
                        result.getString("phone"),
                        result.getString("address"),
                        result.getDouble("salary"),
                        result.getDouble("credit_limit")
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }
}