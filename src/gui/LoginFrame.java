package gui;

import dao.UserDAO;
import model.User;
import model.Customer;

import dao.CustomerDAO;
import gui.customer.CustomerDashboard;
import gui.admin.AdminDashboard;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    private UserDAO userDAO;

    public LoginFrame() {

        userDAO = new UserDAO();

        setTitle("SharkPay - Login");

        setSize(500, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        buildGUI();
    }

    private void buildGUI() {

        JPanel mainPanel =
                new JPanel();

        mainPanel.setBackground(
                UIStyle.CREAM
        );

        mainPanel.setLayout(
                new GridBagLayout()
        );

        JPanel card =
                UIStyle.styleCard();

        card.setPreferredSize(
                new Dimension(380, 470)
        );

        card.setLayout(
                new GridBagLayout()
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets =
                new Insets(8, 30, 8, 30);

        // =========================
        // TITLE
        // =========================

        JLabel title =
                new JLabel("SHARKPAY");

        title.setFont(UIStyle.TITLE_FONT);
        title.setForeground(UIStyle.RED);

        title.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        gbc.gridy = 0;

        card.add(title, gbc);

        // =========================
        // SUBTITLE
        // =========================

        JLabel subtitle =
                new JLabel(
                        "LOAN MANAGEMENT SYSTEM"
                );

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        subtitle.setForeground(
                UIStyle.GOLD
        );

        subtitle.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        gbc.gridy = 1;

        card.add(subtitle, gbc);

        // =========================
        // WELCOME
        // =========================

        JLabel welcome =
                new JLabel("Welcome Back");

        welcome.setFont(
                UIStyle.HEADER_FONT
        );

        welcome.setForeground(
                UIStyle.DARK_TEXT
        );

        welcome.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        gbc.gridy = 2;
        gbc.insets =
                new Insets(30, 30, 8, 30);

        card.add(welcome, gbc);

        // =========================
        // USERNAME
        // =========================

        JLabel usernameLabel =
                UIStyle.styleLabel(
                        "Username"
                );

        gbc.gridy = 3;
        gbc.insets =
                new Insets(8, 30, 3, 30);

        card.add(usernameLabel, gbc);

        usernameField =
                UIStyle.styleTextField();

        gbc.gridy = 4;

        card.add(usernameField, gbc);

        // =========================
        // PASSWORD
        // =========================

        JLabel passwordLabel =
                UIStyle.styleLabel(
                        "Password"
                );

        gbc.gridy = 5;

        card.add(passwordLabel, gbc);

        passwordField =
                UIStyle.stylePasswordField();

        gbc.gridy = 6;

        card.add(passwordField, gbc);

	     // =========================
	     // LOGIN BUTTON
	     // =========================
	
	     JButton loginButton =
	             UIStyle.styleGoldButton(
	                     "LOGIN"
	             );
	
	     loginButton.addActionListener(
	             e -> login()
	     );
	
	     gbc.gridy = 7;
	
	     gbc.insets =
	             new Insets(
	                     25, 30, 5, 30
	             );
	
	     card.add(loginButton, gbc);
	
	     // =========================
	     // REGISTER BUTTON
	     // =========================
	
	     JButton registerButton =
	             UIStyle.styleRedButton(
	                     "REGISTER"
	             );
	
	     registerButton.addActionListener(e -> {
	
	         setVisible(false);
	
	         new RegisterFrame(this)
	                 .setVisible(true);
	     });
	
	     gbc.gridy = 8;
	
	     gbc.insets =
	             new Insets(
	                     5, 30, 15, 30
	             );
	
	     card.add(registerButton, gbc);
	
	        mainPanel.add(card);
	
	        add(mainPanel);
	    }

    private void login() {

        String username =
                usernameField.getText()
                        .trim();

        String password =
                new String(
                        passwordField.getPassword()
                );

        if (username.isEmpty()
                || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter your username and password.",
                    "Login",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        User user =
                userDAO.login(
                        username,
                        password
                );

        if (user == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid username or password.",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        dispose();

        if (user.getRole().equalsIgnoreCase(
                "ADMIN")) {

            new AdminDashboard(user)
                    .setVisible(true);

        } else {

            CustomerDAO customerDAO =
                    new CustomerDAO();

            Customer customer =
                    customerDAO.getCustomerByUserId(
                            user.getUserId()
                    );

            if (customer == null) {

                JOptionPane.showMessageDialog(
                        null,
                        "Customer account was not found."
                );

                new LoginFrame()
                        .setVisible(true);

                return;
            }

            new CustomerDashboard(
                    user,
                    customer
            ).setVisible(true);
        }
    }
}