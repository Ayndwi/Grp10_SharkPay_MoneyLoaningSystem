package gui.customer;

import dao.LoanDAO;
import gui.UIStyle;
import model.Customer;
import model.Loan;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class HistoryFrame extends JDialog {

    private Customer customer;

    private LoanDAO loanDAO;

    private JTable table;

    private NumberFormat currency =
            NumberFormat.getCurrencyInstance(
                    new Locale("en", "PH")
            );

    public HistoryFrame(
            JFrame parent,
            Customer customer) {

        super(
                parent,
                "Loan History",
                true
        );

        this.customer = customer;

        loanDAO =
                new LoanDAO();

        setSize(850, 500);
        setLocationRelativeTo(parent);
        setResizable(false);

        buildGUI();

        loadHistory();
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
                        "LOAN HISTORY"
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
                "Amount",
                "Interest",
                "Total",
                "Balance",
                "Application Date",
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

        JButton close =
                UIStyle.styleRedButton(
                        "CLOSE"
                );

        close.addActionListener(
                e -> dispose()
        );

        JPanel bottom =
                new JPanel();

        bottom.setOpaque(false);

        bottom.add(close);

        main.add(
                bottom,
                BorderLayout.SOUTH
        );

        add(main);
    }

    private void loadHistory() {

        DefaultTableModel model =
                (DefaultTableModel)
                        table.getModel();

        model.setRowCount(0);

        List<Loan> loans =
                loanDAO.getLoansByCustomerId(
                        customer.getCustomerId()
                );

        for (Loan loan : loans) {

            model.addRow(
                    new Object[]{
                            loan.getLoanId(),

                            currency.format(
                                    loan.getLoanAmount()
                            ),

                            loan.getInterestRate()
                                    + "%",

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