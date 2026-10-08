package gui.admin;

import dao.CreditHistoryDAO;
import dao.LienDAO;
import dao.LoanDAO;
import gui.UIStyle;
import model.CreditHistory;
import model.Loan;
import model.Lien;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class CreditFrame extends JDialog {

    private CreditHistoryDAO creditDAO;
    private LoanDAO loanDAO;
    private LienDAO lienDAO;

    private JTable historyTable;

    public CreditFrame(JFrame parent) {

        super(
                parent,
                "Credit History",
                true
        );

        creditDAO =
                new CreditHistoryDAO();

        loanDAO =
                new LoanDAO();

        lienDAO =
                new LienDAO();

        setSize(850, 550);
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
                        "CREDIT HISTORY"
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
                "Record ID",
                "Customer ID",
                "Loan ID",
                "Payment Status",
                "Missed",
                "Late"
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

        historyTable =
                new JTable(model);

        UIStyle.styleTable(
                historyTable
        );

        JScrollPane scrollPane =
                new JScrollPane(
                        historyTable
                );

        main.add(
                scrollPane,
                BorderLayout.CENTER
        );

        JPanel bottom =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                10,
                                5
                        )
                );

        bottom.setOpaque(false);

        JButton lienButton =
                UIStyle.styleGoldButton(
                        "ADD LIEN"
                );

        lienButton.addActionListener(
                e -> addLien()
        );

        JButton refresh =
                UIStyle.styleRedButton(
                        "REFRESH"
                );

        refresh.addActionListener(
                e -> loadHistory()
        );

        JButton close =
                UIStyle.styleRedButton(
                        "CLOSE"
                );

        close.addActionListener(
                e -> dispose()
        );

        bottom.add(lienButton);
        bottom.add(refresh);
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
                        historyTable.getModel();

        model.setRowCount(0);

        List<CreditHistory> history =
                creditDAO.getAllHistory();

        for (CreditHistory record :
                history) {

            model.addRow(
                    new Object[]{
                            record.getCreditHistoryId(),
                            record.getCustomerId(),
                            record.getLoanId() == null
                                    ? "-"
                                    : record.getLoanId(),
                            record.getPaymentStatus(),
                            record.getMissedPayments(),
                            record.getLatePayments()
                    }
            );
        }
    }

    private void addLien() {

        int selectedRow =
                historyTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a credit history record first.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        Object loanValue =
                historyTable.getValueAt(
                        selectedRow,
                        2
                );

        if (loanValue.equals("-")) {

            JOptionPane.showMessageDialog(
                    this,
                    "This record is not connected to a loan.",
                    "Cannot Add Lien",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int customerId =
                Integer.parseInt(
                        historyTable
                                .getValueAt(
                                        selectedRow,
                                        1
                                )
                                .toString()
                );

        int loanId =
                Integer.parseInt(
                        loanValue.toString()
                );

        Loan loan =
                findLoan(loanId);

        if (loan == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Loan could not be found.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        if (!loan.getStatus().equalsIgnoreCase(
                "OVERDUE")) {

            JOptionPane.showMessageDialog(
                    this,
                    "A lien can only be added to an overdue loan.",
                    "Cannot Add Lien",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String reason =
                JOptionPane.showInputDialog(
                        this,
                        "Enter reason for lien:"
                );

        if (reason == null
                || reason.trim().isEmpty()) {

            return;
        }

        Lien lien =
                new Lien(
                        0,
                        customerId,
                        loanId,
                        reason,
                        LocalDate.now(),
                        "ACTIVE"
                );

        boolean success =
                lienDAO.addLien(lien);

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Lien successfully added.",
                    "Lien Added",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to add lien.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private Loan findLoan(int loanId) {

        List<Loan> loans =
                loanDAO.getAllLoans();

        for (Loan loan : loans) {

            if (loan.getLoanId() == loanId) {

                return loan;
            }
        }

        return null;
    }
}