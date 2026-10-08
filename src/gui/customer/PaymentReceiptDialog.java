package gui.customer;

import gui.UIStyle;
import model.Loan;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;

public class PaymentReceiptDialog extends JDialog {

    private NumberFormat currency =
            NumberFormat.getCurrencyInstance(
                    new Locale("en", "PH")
            );

    public PaymentReceiptDialog(
            JFrame parent,
            Loan loan,
            double paymentAmount,
            LocalDate paymentDate,
            String paymentMethod) {

        super(
                parent,
                "Payment Receipt",
                true
        );

        setSize(450, 500);
        setLocationRelativeTo(parent);
        setResizable(false);

        buildGUI(
                loan,
                paymentAmount,
                paymentDate,
                paymentMethod
        );
    }

    private void buildGUI(
            Loan loan,
            double paymentAmount,
            LocalDate paymentDate,
            String paymentMethod) {

        JPanel main =
                new JPanel(
                        new GridBagLayout()
                );

        main.setBackground(
                UIStyle.CREAM
        );

        main.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        30,
                        20,
                        30
                )
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.gridx = 0;
        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.insets =
                new Insets(
                        7,
                        5,
                        7,
                        5
                );

        // =========================
        // TITLE
        // =========================

        JLabel title =
                new JLabel(
                        "PAYMENT RECEIPT"
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
        // SUCCESS MESSAGE
        // =========================

        JLabel success =
                new JLabel(
                        "Payment Successfully Recorded"
                );

        success.setFont(
                UIStyle.HEADER_FONT
        );

        success.setForeground(
                UIStyle.GOLD
        );

        success.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        gbc.gridy = 1;

        gbc.insets =
                new Insets(
                        10,
                        5,
                        20,
                        5
                );

        main.add(
                success,
                gbc
        );

        // Reset spacing
        gbc.insets =
                new Insets(
                        7,
                        5,
                        7,
                        5
                );

        // =========================
        // LOAN ID
        // =========================

        addDetail(
                main,
                gbc,
                2,
                "Loan ID:",
                String.valueOf(
                        loan.getLoanId()
                )
        );

        // =========================
        // PAYMENT DATE
        // =========================

        addDetail(
                main,
                gbc,
                3,
                "Payment Date:",
                paymentDate.toString()
        );

        // =========================
        // PAYMENT METHOD
        // =========================

        addDetail(
                main,
                gbc,
                4,
                "Payment Method:",
                paymentMethod
        );

        // =========================
        // PAYMENT AMOUNT
        // =========================

        addDetail(
                main,
                gbc,
                5,
                "Amount Paid:",
                currency.format(
                        paymentAmount
                )
        );

        // =========================
        // REMAINING BALANCE
        // =========================

        addDetail(
                main,
                gbc,
                6,
                "Remaining Balance:",
                currency.format(
                        loan.getRemainingBalance()
                )
        );

        // =========================
        // LOAN STATUS
        // =========================

        addDetail(
                main,
                gbc,
                7,
                "Loan Status:",
                loan.getStatus()
        );

        // =========================
        // CLOSE BUTTON
        // =========================

        JButton closeButton =
                UIStyle.styleGoldButton(
                        "CLOSE"
                );

        closeButton.addActionListener(
                e -> dispose()
        );

        gbc.gridy = 8;

        gbc.insets =
                new Insets(
                        20,
                        5,
                        5,
                        5
                );

        main.add(
                closeButton,
                gbc
        );

        add(main);
    }

    private void addDetail(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            String label,
            String value) {

        JPanel rowPanel =
                new JPanel(
                        new GridLayout(
                                1,
                                2
                        )
                );

        rowPanel.setOpaque(false);

        JLabel labelText =
                new JLabel(label);

        labelText.setFont(
                UIStyle.NORMAL_FONT
        );

        labelText.setForeground(
                UIStyle.DARK_TEXT
        );

        JLabel valueText =
                new JLabel(value);

        valueText.setFont(
                UIStyle.NORMAL_FONT
        );

        valueText.setForeground(
                UIStyle.RED
        );

        valueText.setHorizontalAlignment(
                SwingConstants.RIGHT
        );

        rowPanel.add(labelText);
        rowPanel.add(valueText);

        gbc.gridy = row;

        panel.add(
                rowPanel,
                gbc
        );
    }
}
