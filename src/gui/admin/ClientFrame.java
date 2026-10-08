package gui.admin;

import dao.CustomerDAO;
import gui.UIStyle;
import model.Customer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ClientFrame extends JDialog {

    private CustomerDAO customerDAO;

    private JTable table;

    private NumberFormat currency =
            NumberFormat.getCurrencyInstance(
                    new Locale("en", "PH")
            );

    public ClientFrame(JFrame parent) {

        super(
                parent,
                "Registered Clients",
                true
        );

        customerDAO =
                new CustomerDAO();

        setSize(850, 500);
        setLocationRelativeTo(parent);
        setResizable(false);

        buildGUI();

        loadClients();
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
                        "REGISTERED CLIENTS"
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
                "ID",
                "Name",
                "Email",
                "Phone",
                "Salary",
                "Credit Limit"
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

    private void loadClients() {

        DefaultTableModel model =
                (DefaultTableModel)
                        table.getModel();

        model.setRowCount(0);

        List<Customer> customers =
                customerDAO.getAllCustomers();

        for (Customer customer :
                customers) {

            model.addRow(
                    new Object[]{
                            customer.getCustomerId(),

                            customer.getFullName(),

                            customer.getEmail(),

                            customer.getPhone(),

                            currency.format(
                                    customer.getSalary()
                            ),

                            currency.format(
                                    customer.getCreditLimit()
                            )
                    }
            );
        }
    }
}