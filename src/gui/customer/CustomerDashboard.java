package gui.customer;

import dao.*;
import gui.LoginFrame;
import gui.UIStyle;
import model.*;
import manager.LoanManager;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class CustomerDashboard extends JFrame {

    private User user;
    private Customer customer;

    private LoanManager loanManager;
    private PaymentDAO paymentDAO;
    private CustomerDAO customerDAO;

    private JLabel balanceLabel;
    private JLabel creditLimitLabel;
    private JLabel loanAmountLabel;
    private JLabel monthlyPaymentLabel;
    private JLabel paymentNumberLabel;
    private JLabel dueDateLabel;
    private JLabel statusLabel;

    private NumberFormat currency =
            NumberFormat.getCurrencyInstance(
                    new Locale("en", "PH")
            );

    public CustomerDashboard(
            User user,
            Customer customer) {

        this.user = user;
        this.customer = customer;

        loanManager =
                new LoanManager();

        paymentDAO =
                new PaymentDAO();
        
        customerDAO = new CustomerDAO();

        setTitle(
                "SharkPay - Customer Dashboard"
        );

        UIStyle.setupFrame(this);

        buildGUI();

        refreshLoan();
    }

    private void buildGUI() {

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout(20, 20)
                );

        mainPanel.setBackground(
                UIStyle.CREAM
        );

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        25, 30, 25, 30
                )
        );

        // =========================
        // HEADER
        // =========================

        JPanel header =
                new JPanel(
                        new BorderLayout()
                );

        header.setOpaque(false);

        JLabel title =
                new JLabel(
                        "Customer Dashboard"
                );

        title.setFont(
                UIStyle.TITLE_FONT
        );

        title.setForeground(
                UIStyle.RED
        );

        JLabel welcome =
                new JLabel(
                        "Welcome, "
                        + customer.getFullName()
                );

        welcome.setFont(
                UIStyle.NORMAL_FONT
        );

        welcome.setForeground(
                UIStyle.DARK_TEXT
        );

        header.add(
                title,
                BorderLayout.WEST
        );

        header.add(
                welcome,
                BorderLayout.EAST
        );

        mainPanel.add(
                header,
                BorderLayout.NORTH
        );

        // =========================
        // CENTER
        // =========================

        JPanel center =
                new JPanel(
                        new BorderLayout(15, 15)
                );

        center.setOpaque(false);

        // =========================
        // ACCOUNT CARDS
        // =========================

        JPanel cards =
                new JPanel(
                        new GridLayout(
                                1,
                                3,
                                15,
                                0
                        )
                );

        cards.setOpaque(false);

        cards.add(
                createInfoCard(
                        "MONTHLY SALARY",
                        currency.format(
                                customer.getSalary()
                        )
                )
        );

        creditLimitLabel = new JLabel(
                currency.format(customer.getCreditLimit())
        );

        cards.add(
                createInfoCard(
                        "CREDIT LIMIT",
                        creditLimitLabel
                )
        );

        balanceLabel =
                new JLabel("₱0.00");

        cards.add(
                createInfoCard(
                        "CURRENT DUE",
                        balanceLabel
                )
        );

        center.add(
                cards,
                BorderLayout.NORTH
        );

        // =========================
        // CURRENT LOAN
        // =========================

        JPanel loanCard =
                UIStyle.styleCard();

        loanCard.setLayout(
                new GridBagLayout()
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.gridx = 0;

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.insets =
                new Insets(
                        7,
                        15,
                        7,
                        15
                );

        // =========================
        // LOAN TITLE
        // =========================

        JLabel loanTitle =
                UIStyle.styleLabel(
                        "CURRENT LOAN"
                );

        loanTitle.setFont(
                UIStyle.HEADER_FONT
        );

        loanTitle.setForeground(
                UIStyle.RED
        );

        gbc.gridy = 0;

        loanCard.add(
                loanTitle,
                gbc
        );

        // =========================
        // LOAN AMOUNT
        // =========================

        loanAmountLabel =
                new JLabel(
                        "No active loan"
                );

        loanAmountLabel.setFont(
                UIStyle.NORMAL_FONT
        );

        loanAmountLabel.setForeground(
                UIStyle.DARK_TEXT
        );

        gbc.gridy = 1;

        loanCard.add(
                loanAmountLabel,
                gbc
        );

        // =========================
        // MONTHLY PAYMENT
        // =========================

        monthlyPaymentLabel =
                new JLabel(
                        "Monthly Payment: -"
                );

        monthlyPaymentLabel.setFont(
                UIStyle.NORMAL_FONT
        );

        monthlyPaymentLabel.setForeground(
                UIStyle.DARK_TEXT
        );

        gbc.gridy = 2;

        loanCard.add(
                monthlyPaymentLabel,
                gbc
        );

        // =========================
        // PAYMENT NUMBER
        // =========================

        paymentNumberLabel =
                new JLabel(
                        "Payment: -"
                );

        paymentNumberLabel.setFont(
                UIStyle.NORMAL_FONT
        );

        paymentNumberLabel.setForeground(
                UIStyle.DARK_TEXT
        );

        gbc.gridy = 3;

        loanCard.add(
                paymentNumberLabel,
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

        dueDateLabel.setForeground(
                UIStyle.DARK_TEXT
        );

        gbc.gridy = 4;

        loanCard.add(
                dueDateLabel,
                gbc
        );

        // =========================
        // STATUS
        // =========================

        statusLabel =
                new JLabel(
                        "Status: -"
                );

        statusLabel.setFont(
                UIStyle.NORMAL_FONT
        );

        gbc.gridy = 5;

        loanCard.add(
                statusLabel,
                gbc
        );

        center.add(
                loanCard,
                BorderLayout.CENTER
        );

        mainPanel.add(
                center,
                BorderLayout.CENTER
        );

        // =========================
        // BUTTONS
        // =========================

        JPanel buttons =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                15,
                                5
                        )
                );

        buttons.setOpaque(false);

        JButton loanButton =
                UIStyle.styleGoldButton(
                        "APPLY FOR LOAN"
                );

        loanButton.addActionListener(
                e -> openLoan()
        );

        JButton paymentButton =
                UIStyle.styleRedButton(
                        "MAKE PAYMENT"
                );

        paymentButton.addActionListener(
                e -> openPayment()
        );

        JButton historyButton =
                UIStyle.styleRedButton(
                        "LOAN HISTORY"
                );

        historyButton.addActionListener(
                e -> openHistory()
        );

        JButton logoutButton =
                UIStyle.styleRedButton(
                        "LOGOUT"
                );

        logoutButton.addActionListener(
                e -> logout()
        );

        buttons.add(loanButton);
        buttons.add(paymentButton);
        buttons.add(historyButton);
        buttons.add(logoutButton);

        mainPanel.add(
                buttons,
                BorderLayout.SOUTH
        );

        add(mainPanel);
    }

    // ==========================================
    // CREATE INFO CARD
    // ==========================================

    private JPanel createInfoCard(
            String title,
            String value) {

        JLabel valueLabel =
                new JLabel(value);

        return createInfoCard(
                title,
                valueLabel
        );
    }

    private JPanel createInfoCard(
            String title,
            JLabel valueLabel) {

        JPanel panel =
                UIStyle.styleCard();

        panel.setLayout(
                new BorderLayout(5, 5)
        );

        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setFont(
                UIStyle.SMALL_FONT
        );

        titleLabel.setForeground(
                UIStyle.GRAY
        );

        valueLabel.setFont(
                UIStyle.HEADER_FONT
        );

        valueLabel.setForeground(
                UIStyle.RED
        );

        panel.add(
                titleLabel,
                BorderLayout.NORTH
        );

        panel.add(
                valueLabel,
                BorderLayout.CENTER
        );

        return panel;
    }

    // ==========================================
    // REFRESH CURRENT LOAN
    // ==========================================

    private void refreshLoan() {

    	refreshCustomer();
    	
        Loan loan =
                loanManager.getCurrentLoan(
                        customer
                );

        // =========================
        // NO ACTIVE LOAN
        // =========================

        if (loan == null) {

            balanceLabel.setText(
                    currency.format(0)
            );

            loanAmountLabel.setText(
                    "No active loan"
            );

            monthlyPaymentLabel.setText(
                    "Monthly Payment: -"
            );

            paymentNumberLabel.setText(
                    "Payment: -"
            );

            dueDateLabel.setText(
                    "Due Date: -"
            );

            statusLabel.setText(
                    "Status: No Active Loan"
            );

            statusLabel.setForeground(
                    UIStyle.GRAY
            );

            return;
        }

        // =========================
        // CURRENT BALANCE
        // =========================

        balanceLabel.setText(
                currency.format(
                        loan.getRemainingBalance()
                )
        );

        // =========================
        // LOAN INFORMATION
        // =========================

        loanAmountLabel.setText(
                "Loan Amount: "
                + currency.format(
                        loan.getLoanAmount()
                )
                + "     |     Total: "
                + currency.format(
                        loan.getTotalAmount()
                )
                + "     |     Balance: "
                + currency.format(
                        loan.getRemainingBalance()
                )
        );

        // =========================
        // MONTHLY PAYMENT
        // =========================

        double monthlyPayment =
                loan.getTotalAmount()
                / loan.getTermMonths();

        monthlyPaymentLabel.setText(
                "Monthly Payment: "
                + currency.format(
                        monthlyPayment
                )
        );

        // =========================
        // PAYMENT NUMBER
        // =========================

        int paymentsMade =
                paymentDAO.countPaymentsByLoanId(
                        loan.getLoanId()
                );

        int paymentNumber =
                paymentsMade + 1;

        /*
         * If the loan is already fully paid,
         * don't display another payment.
         */
        if (loan.getRemainingBalance() <= 0
                || loan.getStatus().equalsIgnoreCase("PAID")) {

            paymentNumberLabel.setText(
                    "Payments Completed: "
                    + paymentsMade
            );

        } else {

            paymentNumberLabel.setText(
                    "Next Payment: "
                    + paymentNumber
                    + " of "
                    + loan.getTermMonths()
            );
        }

        // =========================
        // CURRENT MONTHLY DUE DATE
        // =========================

        LocalDate monthlyDueDate =
                loan.getApplicationDate()
                    .plusMonths(paymentNumber);

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(
                        "MMMM dd, yyyy"
                );

        dueDateLabel.setText(
                "Next Due Date: "
                + monthlyDueDate.format(
                        formatter
                )
        );

        // =========================
        // LOAN STATUS
        // =========================

        statusLabel.setText(
                "Status: "
                + loan.getStatus()
        );

        if (loan.getStatus().equalsIgnoreCase(
                "OVERDUE")) {

            statusLabel.setForeground(
                    UIStyle.RED
            );

        } else {

            statusLabel.setForeground(
                    UIStyle.GREEN
            );
        }
    }

    // ==========================================
    // OPEN LOAN FRAME
    // ==========================================

    private void openLoan() {

        new LoanFrame(
                this,
                customer
        ).setVisible(true);

        refreshLoan();
    }

    // ==========================================
    // OPEN PAYMENT FRAME
    // ==========================================

    private void openPayment() {

        new PaymentFrame(
                this,
                customer
        ).setVisible(true);

        refreshCustomer();
        refreshLoan();
    }

    // ==========================================
    // OPEN HISTORY
    // ==========================================

    private void openHistory() {

        new HistoryFrame(
                this,
                customer
        ).setVisible(true);
    }
    
    
    
    private void refreshCustomer() {

        Customer updatedCustomer =
                customerDAO.getCustomerByUserId(
                        customer.getUserId()
                );

        if (updatedCustomer != null) {

            customer = updatedCustomer;
        }
    }
    

    // ==========================================
    // LOGOUT
    // ==========================================

    private void logout() {

        dispose();

        new LoginFrame()
                .setVisible(true);
    }
}