package gui.customer;

import gui.UIStyle;
import manager.LoanManager;
import model.Customer;
import model.Loan;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;

public class LoanFrame extends JDialog {

    private Customer customer;

    private LoanManager loanManager;

    private JTextField amountField;

    private JComboBox<Integer> termBox;

    private JLabel totalLabel;
    private JLabel dueDateLabel;

    private NumberFormat currency =
            NumberFormat.getCurrencyInstance(
                    new Locale("en", "PH")
            );

    public LoanFrame(
            JFrame parent,
            Customer customer) {

        super(
                parent,
                "Apply for Loan",
                true
        );

        this.customer = customer;

        loanManager =
                new LoanManager();

        setSize(500, 520);
        setLocationRelativeTo(parent);
        setResizable(false);

        buildGUI();
    }

    private void buildGUI() {

        JPanel main =
                new JPanel(
                        new GridBagLayout()
                );

        main.setBackground(
                UIStyle.CREAM
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.gridx = 0;

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.insets =
                new Insets(
                        8,
                        35,
                        8,
                        35
                );

        JLabel title =
                new JLabel(
                        "APPLY FOR LOAN"
                );

        title.setFont(
                UIStyle.TITLE_FONT
        );

        title.setForeground(
                UIStyle.RED
        );

        title.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        gbc.gridy = 0;

        main.add(
                title,
                gbc
        );

        // =========================
        // SALARY
        // =========================

        JLabel salary =
                UIStyle.styleLabel(
                        "Monthly Salary: "
                        + currency.format(
                                customer.getSalary()
                        )
                );

        gbc.gridy = 1;

        main.add(
                salary,
                gbc
        );

        // =========================
        // CREDIT LIMIT
        // =========================

        JLabel credit =
                UIStyle.styleLabel(
                        "Credit Limit: "
                        + currency.format(
                                customer.getCreditLimit()
                        )
                );

        gbc.gridy = 2;

        main.add(
                credit,
                gbc
        );

        // =========================
        // AMOUNT
        // =========================

        JLabel amountLabel =
                UIStyle.styleLabel(
                        "Loan Amount"
                );

        gbc.gridy = 3;

        main.add(
                amountLabel,
                gbc
        );

        amountField =
                UIStyle.styleTextField();

        gbc.gridy = 4;

        main.add(
                amountField,
                gbc
        );

        // =========================
        // TERM
        // =========================

        JLabel termLabel =
                UIStyle.styleLabel(
                        "Loan Term"
                );

        gbc.gridy = 5;

        main.add(
                termLabel,
                gbc
        );

        termBox =
                new JComboBox<>(
                        new Integer[]{
                                3,
                                6,
                                9,
                                12
                        }
                );

        UIStyle.styleComboBox(
                termBox
        );

        gbc.gridy = 6;

        main.add(
                termBox,
                gbc
        );

        // =========================
        // TOTAL
        // =========================

        totalLabel =
                new JLabel(
                        "Total Amount: ₱0.00"
                );

        totalLabel.setFont(
                UIStyle.HEADER_FONT
        );

        totalLabel.setForeground(
                UIStyle.RED
        );

        gbc.gridy = 7;

        main.add(
                totalLabel,
                gbc
        );

        // =========================
        // DUE DATE
        // =========================

        dueDateLabel =
                new JLabel(
                        "Due Date: -"
                );

        dueDateLabel.setFont(
                UIStyle.NORMAL_FONT
        );

        gbc.gridy = 8;

        main.add(
                dueDateLabel,
                gbc
        );

        // =========================
        // BUTTONS
        // =========================

        JButton applyButton =
                UIStyle.styleGoldButton(
                        "SUBMIT LOAN"
                );

        applyButton.addActionListener(
                e -> applyLoan()
        );

        gbc.gridy = 9;

        gbc.insets =
                new Insets(
                        20,
                        35,
                        8,
                        35
                );

        main.add(
                applyButton,
                gbc
        );

        JButton cancelButton =
                UIStyle.styleRedButton(
                        "CANCEL"
                );

        cancelButton.addActionListener(
                e -> dispose()
        );

        gbc.gridy = 10;

        main.add(
                cancelButton,
                gbc
        );

        add(main);
    }

    private void applyLoan() {

        // =========================
        // CHECK EXISTING LOAN
        // =========================

        Loan currentLoan =
                loanManager.getCurrentLoan(
                        customer
                );

        if (currentLoan != null
                && currentLoan.getRemainingBalance() > 0) {

            UIStyle.showWarning(
                    this,
                    "You cannot apply for a new loan.\n\n"
                    + "You still have an unpaid loan.\n"
                    + "Remaining Balance: "
                    + currency.format(
                            currentLoan.getRemainingBalance()
                    )
            );

            return;
        }

        // =========================
        // GET LOAN AMOUNT
        // =========================

        double amount;

        try {

            amount =
                    Double.parseDouble(
                            amountField
                                    .getText()
                                    .trim()
                    );

        } catch (NumberFormatException e) {

            UIStyle.showWarning(
                    this,
                    "Please enter a valid loan amount."
            );

            return;
        }

        // =========================
        // GET TERM
        // =========================

        int term =
                (Integer)
                        termBox.getSelectedItem();

        double interestRate = 5.0;

        // =========================
        // CHECK ELIGIBILITY
        // =========================

        if (!loanManager.isEligible(
                customer,
                amount)) {

            UIStyle.showWarning(
                    this,
                    "You are not eligible for this loan amount.\n\n"
                    + "Maximum credit limit: "
                    + currency.format(
                            customer.getCreditLimit()
                    )
            );

            return;
        }

        // =========================
        // CREATE LOAN
        // =========================

        Loan loan =
                loanManager.createLoan(
                        customer,
                        amount,
                        interestRate,
                        term
                );

        if (loan == null) {

            UIStyle.showError(
                    this,
                    "Unable to create the loan."
            );

            return;
        }

        // =========================
        // SUCCESS
        // =========================

        UIStyle.showSuccess(
                this,
                "Loan approved!\n\n"
                + "Loan Amount: "
                + currency.format(
                        loan.getLoanAmount()
                )
                + "\nTotal Amount: "
                + currency.format(
                        loan.getTotalAmount()
                )
                + "\nDue Date: "
                + loan.getDueDate()
        );

        dispose();
    }
}