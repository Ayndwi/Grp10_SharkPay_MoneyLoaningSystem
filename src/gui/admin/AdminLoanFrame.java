package gui.admin;

import dao.LoanDAO;
import gui.UIStyle;
import model.Loan;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class AdminLoanFrame extends JDialog {

    private LoanDAO loanDAO;

    private JTable table;

    private NumberFormat currency =
            NumberFormat.getCurrencyInstance(
                    new Locale("en", "PH")
            );

    public AdminLoanFrame(JFrame parent) {

        super(
                parent,
                "Loan Management",
                true
        );

        loanDAO =
                new LoanDAO();

        setSize(900, 550);
        setLocationRelativeTo(parent);
        setResizable(false);

        buildGUI();

        loadLoans();
    }

    private void buildGUI() {

        JPanel main =
                new JPanel(
                        new BorderLayout(
                                15,
                                15
                        )
                );

        main.setBackground(
                UIStyle.CREAM
        );

        main.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 25, 20, 25
                )
        );

        JLabel title =
                new JLabel(
                        "LOAN MANAGEMENT"
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

        main.add(
                title,
                BorderLayout.NORTH
        );

        String[] columns = {
                "Loan ID",
                "Customer ID",
                "Amount",
                "Total",
                "Balance",
                "Application",
                "Due Date",
                "Status"
        };

        DefaultTableModel model =
                new DefaultTableModel(
                        columns,
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column) {

                        return false;
                    }
                };

        table =
                new JTable(model);

        UIStyle.styleTable(table);

        JScrollPane scrollPane =
                new JScrollPane(table);

        main.add(
                scrollPane,
                BorderLayout.CENTER
        );

        JPanel bottom =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER
                        )
                );

        bottom.setOpaque(false);

        JButton refresh =
                UIStyle.styleGoldButton(
                        "REFRESH"
                );

        refresh.addActionListener(
                e -> loadLoans()
        );

        JButton close =
                UIStyle.styleRedButton(
                        "CLOSE"
                );

        close.addActionListener(
                e -> dispose()
        );

        bottom.add(refresh);
        bottom.add(close);

        main.add(
                bottom,
                BorderLayout.SOUTH
        );

        add(main);
    }

    private void loadLoans() {

        DefaultTableModel model =
                (DefaultTableModel)
                        table.getModel();

        model.setRowCount(0);

        List<Loan> loans =
                loanDAO.getAllLoans();

        for (Loan loan : loans) {

            model.addRow(
                    new Object[]{
                            loan.getLoanId(),

                            loan.getCustomerId(),

                            currency.format(
                                    loan.getLoanAmount()
                            ),

                            currency.format(
                                    loan.getTotalAmount()
                            ),

                            currency.format(
                                    loan.getRemainingBalance()
                            ),

                            loan.getApplicationDate(),

                            loan.getDueDate(),

                            loan.getStatus()
                    }
            );
        }
    }
}