package gui;

import manager.RegistrationManager;

import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {

    // =========================================================
    // INPUT FIELDS
    // =========================================================

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;

    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField addressField;
    private JTextField salaryField;


    private RegistrationManager registrationManager;


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public RegisterFrame(JFrame parent) {

        // Create the RegistrationManager.
        registrationManager = new RegistrationManager();

        // Window title.
        setTitle("SharkPay - Register");

        // Set the application's standard frame settings.
        UIStyle.setupFrame(this);

        // Registration screen needs a little more height.
        setSize(900, 700);

        // Center the window.
        setLocationRelativeTo(null);

        // Use BorderLayout for the main window.
        setLayout(new BorderLayout());


        // =====================================================
        // TITLE
        // =====================================================

        JLabel title = new JLabel(
                "CREATE CUSTOMER ACCOUNT",
                SwingConstants.CENTER
        );

        title.setFont(UIStyle.TITLE_FONT);
        title.setForeground(UIStyle.RED);

        title.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        10,
                        20,
                        10
                )
        );

        add(title, BorderLayout.NORTH);


        // =====================================================
        // FORM PANEL
        // =====================================================

        JPanel formPanel = new JPanel(
                new GridBagLayout()
        );

        formPanel.setBackground(UIStyle.CREAM);

        GridBagConstraints gbc =
                new GridBagConstraints();

        // Space around each form element.
        gbc.insets =
                new Insets(6, 12, 6, 12);

        // Allow components to stretch horizontally.
        gbc.fill =
                GridBagConstraints.HORIZONTAL;


        // =====================================================
        // CREATE INPUT FIELDS
        // =====================================================

        usernameField =
                UIStyle.styleTextField();

        passwordField =
                UIStyle.stylePasswordField();

        confirmPasswordField =
                UIStyle.stylePasswordField();

        firstNameField =
                UIStyle.styleTextField();

        lastNameField =
                UIStyle.styleTextField();

        emailField =
                UIStyle.styleTextField();

        phoneField =
                UIStyle.styleTextField();

        addressField =
                UIStyle.styleTextField();

        salaryField =
                UIStyle.styleTextField();


        // Start at row 0.
        int row = 0;


        // =====================================================
        // USERNAME
        // =====================================================

        addField(
                formPanel,
                gbc,
                row++,
                "Username:",
                usernameField
        );


        // =====================================================
        // PASSWORD
        // =====================================================

        addField(
                formPanel,
                gbc,
                row++,
                "Password:",
                passwordField
        );


        // =====================================================
        // CONFIRM PASSWORD
        // =====================================================

        addField(
                formPanel,
                gbc,
                row++,
                "Confirm Password:",
                confirmPasswordField
        );


        // =====================================================
        // FIRST NAME
        // =====================================================

        addField(
                formPanel,
                gbc,
                row++,
                "First Name:",
                firstNameField
        );


        // =====================================================
        // LAST NAME
        // =====================================================

        addField(
                formPanel,
                gbc,
                row++,
                "Last Name:",
                lastNameField
        );


        // =====================================================
        // EMAIL
        // =====================================================

        addField(
                formPanel,
                gbc,
                row++,
                "Email:",
                emailField
        );


        // =====================================================
        // PHONE
        // =====================================================

        addField(
                formPanel,
                gbc,
                row++,
                "Phone:",
                phoneField
        );


        // =====================================================
        // ADDRESS
        // =====================================================

        addField(
                formPanel,
                gbc,
                row++,
                "Address:",
                addressField
        );


        // =====================================================
        // SALARY
        // =====================================================

        addField(
                formPanel,
                gbc,
                row++,
                "Monthly Salary:",
                salaryField
        );


        // Add form to the center of the window.
        add(
                formPanel,
                BorderLayout.CENTER
        );


        // =====================================================
        // BUTTON PANEL
        // =====================================================

        JPanel buttonPanel =
                new JPanel();

        buttonPanel.setBackground(
                UIStyle.CREAM
        );


        // Register button.
        JButton registerButton =
                UIStyle.styleGoldButton(
                        "REGISTER"
                );


        // Back button.
        JButton backButton =
                UIStyle.styleRedButton(
                        "BACK TO LOGIN"
                );


        // Add buttons to panel.
        buttonPanel.add(registerButton);
        buttonPanel.add(backButton);


        // Add button panel to bottom.
        add(
                buttonPanel,
                BorderLayout.SOUTH
        );


        // =====================================================
        // REGISTER BUTTON ACTION
        // =====================================================

        registerButton.addActionListener(
                e -> register()
        );


        // =====================================================
        // BACK BUTTON ACTION
        // =====================================================

        backButton.addActionListener(e -> {

            // Close registration window.
            dispose();

            // Show the original login window.
            parent.setVisible(true);
        });


        // =====================================================
        // WINDOW CLOSE
        // =====================================================

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );
    }


    // =========================================================
    // ADD FIELD HELPER METHOD
    // =========================================================

    private void addField(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            String labelText,
            JComponent field) {


        // -----------------------------------------------------
        // LABEL
        // -----------------------------------------------------

        gbc.gridx = 0;
        gbc.gridy = row;

        // Label doesn't need to take extra space.
        gbc.weightx = 0;

        JLabel label =
                UIStyle.styleLabel(labelText);

        panel.add(
                label,
                gbc
        );


        // -----------------------------------------------------
        // INPUT FIELD
        // -----------------------------------------------------

        gbc.gridx = 1;

        // Input field gets the extra horizontal space.
        gbc.weightx = 1;

        panel.add(
                field,
                gbc
        );
    }


    // =========================================================
    // REGISTER METHOD
    // =========================================================

    private void register() {


        // =====================================================
        // GET VALUES FROM FORM
        // =====================================================

        String username =
                usernameField.getText().trim();

        String password =
                new String(
                        passwordField.getPassword()
                );

        String confirmPassword =
                new String(
                        confirmPasswordField.getPassword()
                );

        String firstName =
                firstNameField.getText().trim();

        String lastName =
                lastNameField.getText().trim();

        String email =
                emailField.getText().trim();

        String phone =
                phoneField.getText().trim();

        String address =
                addressField.getText().trim();

        String salaryText =
                salaryField.getText().trim();


        // =====================================================
        // CHECK REQUIRED FIELDS
        // =====================================================

        if (username.isEmpty()
                || password.isEmpty()
                || confirmPassword.isEmpty()
                || firstName.isEmpty()
                || lastName.isEmpty()
                || salaryText.isEmpty()) {

            UIStyle.showWarning(
                    this,
                    "Please fill in all required fields."
            );

            return;
        }


        // =====================================================
        // CHECK PASSWORDS
        // =====================================================

        if (!password.equals(confirmPassword)) {

            UIStyle.showWarning(
                    this,
                    "Passwords do not match."
            );

            return;
        }


        // =====================================================
        // CONVERT SALARY
        // =====================================================

        double salary;

        try {

            salary =
                    Double.parseDouble(
                            salaryText
                    );

        } catch (NumberFormatException e) {

            UIStyle.showWarning(
                    this,
                    "Salary must be a valid number."
            );

            return;
        }


        // =====================================================
        // CHECK SALARY
        // =====================================================

        if (salary <= 0) {

            UIStyle.showWarning(
                    this,
                    "Salary must be greater than zero."
            );

            return;
        }


        // =====================================================
        // REGISTER CUSTOMER
        // =====================================================

        boolean success =
                registrationManager.registerCustomer(
                        username,
                        password,
                        firstName,
                        lastName,
                        email,
                        phone,
                        address,
                        salary
                );


        // =====================================================
        // REGISTRATION SUCCESSFUL
        // =====================================================

        if (success) {

            UIStyle.showSuccess(
                    this,
                    "Registration successful!\n\n"
                    + "You can now log in using your "
                    + "new username and password."
            );


            // Close registration screen.
            dispose();


            // Create a new login screen.
            new LoginFrame().setVisible(true);

        }


        // =====================================================
        // REGISTRATION FAILED
        // =====================================================

        else {

            UIStyle.showError(
                    this,
                    "Registration failed.\n\n"
                    + "The username may already exist "
                    + "or some information is invalid."
            );
        }
    }
}