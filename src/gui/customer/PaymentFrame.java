package gui.customer;

import dao.PaymentDAO;
import gui.UIStyle;
import manager.LoanManager;
import manager.PaymentManager;
import model.Customer;
import model.Loan;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;

public class PaymentFrame extends JDialog {

    private Customer customer;

    private LoanManager loanManager;
    private PaymentManager paymentManager;
    private PaymentDAO paymentDAO;

    private Loan loan;

    private JTextField amountField;
    private JTextField paymentDateField;
    private JComboBox<String> methodBox;

    private NumberFormat currency =
            NumberFormat.getCurrencyInstance(
                    new Locale("en", "PH")
            );

    public PaymentFrame(
            JFrame parent,
            Customer customer) {

        super(
                parent,
                "Make Payment",
                true
        );

        this.customer = customer;

        loanManager =
                new LoanManager();

        paymentManager =
                new PaymentManager();

        paymentDAO =
                new PaymentDAO();

        loan =
                loanManager.getCurrentLoan(
                        customer
                );

        setSize(500, 700);
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

        // =========================
        // TITLE
        // =========================

        JLabel title =
                new JLabel(
                        "MAKE PAYMENT"
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
        // NO ACTIVE LOAN
        // =========================

        if (loan == null) {

            JLabel noLoan =
                    new JLabel(
                            "You currently have no active loan."
                    );

            noLoan.setFont(
                    UIStyle.NORMAL_FONT
            );

            noLoan.setForeground(
                    UIStyle.DARK_TEXT
            );

            noLoan.setHorizontalAlignment(
                    SwingConstants.CENTER
            );

            gbc.gridy = 1;

            main.add(
                    noLoan,
                    gbc
            );

            JButton close =
                    UIStyle.styleRedButton(
                            "CLOSE"
                    );

            close.addActionListener(
                    e -> dispose()
            );

            gbc.gridy = 2;

            main.add(
                    close,
                    gbc
            );

            add(main);

            return;
        }

        // =========================
        // REMAINING BALANCE
        // =========================

        JLabel balance =
                new JLabel(
                        "Remaining Balance: "
                        + currency.format(
                                loan.getRemainingBalance()
                        )
                );

        balance.setFont(
                UIStyle.HEADER_FONT
        );

        balance.setForeground(
                UIStyle.RED
        );

        gbc.gridy = 1;

        main.add(
                balance,
                gbc
        );

        // =========================
        // CALCULATE MONTHLY PAYMENT
        // =========================

        double monthlyPayment =
                loan.getTotalAmount()
                / loan.getTermMonths();

        // =========================
        // MONTHLY PAYMENT
        // =========================

        JLabel monthlyPaymentLabel =
                UIStyle.styleLabel(
                        "Monthly Payment: "
                        + currency.format(
                                monthlyPayment
                        )
                );

        gbc.gridy = 2;

        main.add(
                monthlyPaymentLabel,
                gbc
        );

        // =========================
        // COUNT PAYMENTS
        // =========================

        int paymentsMade =
                paymentDAO.countPaymentsByLoanId(
                        loan.getLoanId()
                );

        int paymentNumber =
                paymentsMade + 1;

        // =========================
        // PAYMENT NUMBER
        // =========================

        JLabel paymentNumberLabel =
                UIStyle.styleLabel(
                        "Payment #: "
                        + paymentNumber
                        + " of "
                        + loan.getTermMonths()
                );

        gbc.gridy = 3;

        main.add(
                paymentNumberLabel,
                gbc
        );

        // =========================
        // CURRENT DUE DATE
        // =========================

        LocalDate monthlyDueDate =
                loan.getApplicationDate()
                    .plusMonths(paymentNumber);

        JLabel dueDate =
                UIStyle.styleLabel(
                        "Due Date: "
                        + monthlyDueDate
                );

        gbc.gridy = 4;

        main.add(
                dueDate,
                gbc
        );

        // =========================
        // PAYMENT AMOUNT
        // =========================

        JLabel amountLabel =
                UIStyle.styleLabel(
                        "Payment Amount"
                );

        gbc.gridy = 5;

        main.add(
                amountLabel,
                gbc
        );

        amountField =
                UIStyle.styleTextField();

        // Automatically show the required
        // monthly payment.
        amountField.setText(
                String.format(
                        "%.2f",
                        monthlyPayment
                )
        );

        gbc.gridy = 6;

        main.add(
                amountField,
                gbc
        );
        
        
	     // =========================
	     // PAYMENT DATE
	     // =========================
	
	     JLabel paymentDateLabel =
	             UIStyle.styleLabel(
	                     "Test Payment Date (YYYY-MM-DD)"
	             );
	
	     gbc.gridy = 7;
	     main.add(
	             paymentDateLabel,
	             gbc
	     );
	
	     paymentDateField =
	             UIStyle.styleTextField();
	
	     // Default to today's date
	     paymentDateField.setText(
	             LocalDate.now().toString()
	     );
	
	     gbc.gridy = 8;
	     main.add(
	             paymentDateField,
	             gbc
	     );
	     
	     

        // =========================
        // PAYMENT METHOD
        // =========================

        JLabel methodLabel =
                UIStyle.styleLabel(
                        "Payment Method"
                );

        gbc.gridy = 9;

        main.add(
                methodLabel,
                gbc
        );

        methodBox =
                new JComboBox<>(
                        new String[]{
                                "Cash",
                                "Bank Transfer",
                                "E-Wallet"
                        }
                );

        UIStyle.styleComboBox(
                methodBox
        );

        gbc.gridy = 10;

        main.add(
                methodBox,
                gbc
        );

        // =========================
        // SUBMIT
        // =========================

        JButton payButton =
                UIStyle.styleGoldButton(
                        "SUBMIT PAYMENT"
                );

        payButton.addActionListener(
                e -> makePayment()
        );

        gbc.gridy = 11;

        gbc.insets =
                new Insets(
                        20,
                        35,
                        8,
                        35
                );

        main.add(
                payButton,
                gbc
        );

        // =========================
        // CANCEL
        // =========================

        JButton cancelButton =
                UIStyle.styleRedButton(
                        "CANCEL"
                );

        cancelButton.addActionListener(
                e -> dispose()
        );

        gbc.gridy = 12;

        gbc.insets =
                new Insets(
                        8,
                        35,
                        8,
                        35
                );

        main.add(
                cancelButton,
                gbc
        );

        add(main);
    }

    private void makePayment() {

        // =========================
        // GET PAYMENT AMOUNT
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
                    "Please enter a valid payment amount."
            );

            return;
        }

        // =========================
        // CHECK POSITIVE AMOUNT
        // =========================

        if (amount <= 0) {

            UIStyle.showWarning(
                    this,
                    "Payment must be greater than zero."
            );

            return;
        }

        // =========================
        // CHECK OVERPAYMENT
        // =========================

        if (amount >
                loan.getRemainingBalance()) {

            UIStyle.showWarning(
                    this,
                    "Payment cannot be greater than "
                    + "the remaining balance."
            );

            return;
        }

        // =========================
        // GET PAYMENT METHOD
        // =========================

        String method =
                (String)
                        methodBox.getSelectedItem();


	     // =========================
	     // GET PAYMENT DATE
	     // =========================
	
	     LocalDate paymentDate;
	
	     try {
	
	         paymentDate =
	                 LocalDate.parse(
	                         paymentDateField
	                                 .getText()
	                                 .trim()
	                 );
	
	     } catch (Exception e) {
	
	         UIStyle.showWarning(
	                 this,
	                 "Invalid payment date.\n"
	                 + "Please use YYYY-MM-DD."
	         );
	
	         return;
	     }
	
	     // =========================
	     // PROCESS PAYMENT
	     // =========================
	
	     boolean success =
	             paymentManager.makePayment(
	                     loan,
	                     amount,
	                     paymentDate,
	                     method
	             );

        // =========================
        // PAYMENT RESULT
        // =========================

	     if (success) {

	    	    PaymentReceiptDialog receipt =
	    	            new PaymentReceiptDialog(
	    	                    (JFrame) getParent(),
	    	                    loan,
	    	                    amount,
	    	                    LocalDate.now(),
	    	                    method
	    	            );

	    	    dispose();

	    	    receipt.setVisible(true);

	    	} else {

            UIStyle.showError(
                    this,
                    "Unable to process payment."
            );
        }
    }
}