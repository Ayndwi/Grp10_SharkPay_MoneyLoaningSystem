package manager;

import dao.UserDAO;
import dao.CustomerDAO;
import model.User;
import model.Customer;

public class RegistrationManager {

    private UserDAO userDAO;
    private CustomerDAO customerDAO;


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public RegistrationManager() {

        // Create objects that allow us to communicate
        // with the database.
        userDAO = new UserDAO();
        customerDAO = new CustomerDAO();
    }


    // =========================================================
    // REGISTER CUSTOMER
    // =========================================================

    public boolean registerCustomer(
            String username,
            String password,
            String firstName,
            String lastName,
            String email,
            String phone,
            String address,
            double salary) {


        if (username == null || username.trim().isEmpty()) {
            return false;
        }

        if (password == null || password.isEmpty()) {
            return false;
        }

        if (firstName == null || firstName.trim().isEmpty()) {
            return false;
        }

        if (lastName == null || lastName.trim().isEmpty()) {
            return false;
        }


        if (salary <= 0) {
            return false;
        }


        if (userDAO.usernameExists(username)) {
            return false;
        }


        User user = new User(
                0,              
                username,
                password,
                "CUSTOMER",    
                "ACTIVE"       
        );


        int userId = userDAO.createUser(user);


        if (userId == -1) {
            return false;
        }

        Customer customer = new Customer(
                0,              
                userId,         
                firstName,
                lastName,
                email,
                phone,
                address,
                salary,
                10000.00       // Default credit limit
        );



        return customerDAO.createCustomer(customer);
    }
}