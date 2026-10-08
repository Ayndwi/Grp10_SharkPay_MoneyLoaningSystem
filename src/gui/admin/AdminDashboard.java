package gui.admin;

import gui.LoginFrame;
import gui.UIStyle;
import model.User;

import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame {

    private User user;

    public AdminDashboard(User user) {

        this.user = user;

        setTitle(
                "SharkPay - Admin Dashboard"
        );

        UIStyle.setupFrame(this);

        buildGUI();
    }

    private void buildGUI() {

        JPanel main =
                new JPanel(
                        new BorderLayout()
                );

        main.setBackground(
                UIStyle.CREAM
        );

        main.setBorder(
                BorderFactory.createEmptyBorder(
                        30, 40, 30, 40
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
                        "ADMIN DASHBOARD"
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
                        + user.getUsername()
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

        main.add(
                header,
                BorderLayout.NORTH
        );

        // =========================
        // OPTIONS
        // =========================

        JPanel options =
                new JPanel(
                        new GridLayout(
                                2,
                                2,
                                20,
                                20
                        )
                );

        options.setOpaque(false);

        JButton clients =
                createMenuButton(
                        "CLIENTS",
                        "View registered customers"
                );

        clients.addActionListener(
                e -> openClients()
        );

        JButton loans =
                createMenuButton(
                        "LOANS",
                        "View all customer loans"
                );

        loans.addActionListener(
                e -> openLoans()
        );

        JButton credit =
                createMenuButton(
                        "CREDIT HISTORY",
                        "View customer credit records"
                );

        credit.addActionListener(
                e -> openCredit()
        );

        JButton logout =
                createMenuButton(
                        "LOGOUT",
                        "Return to login"
                );

        logout.addActionListener(
                e -> logout()
        );

        options.add(clients);
        options.add(loans);
        options.add(credit);
        options.add(logout);

        main.add(
                options,
                BorderLayout.CENTER
        );

        add(main);
    }

    private JButton createMenuButton(
            String title,
            String description) {

        JButton button =
                new JButton(
                        "<html><center>"
                        + "<font size='5'>"
                        + title
                        + "</font><br>"
                        + "<font size='3'>"
                        + description
                        + "</font>"
                        + "</center></html>"
                );

        button.setFont(
                UIStyle.LABEL_FONT
        );

        button.setForeground(
                UIStyle.WHITE
        );

        button.setBackground(
                UIStyle.RED
        );

        button.setFocusPainted(false);

        button.setBorder(
                BorderFactory.createLineBorder(
                        UIStyle.GOLD,
                        2
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        return button;
    }

    private void openClients() {

        new ClientFrame(this)
                .setVisible(true);
    }

    private void openLoans() {

        new AdminLoanFrame(this)
                .setVisible(true);
    }

    private void openCredit() {

        new CreditFrame(this)
                .setVisible(true);
    }

    private void logout() {

        dispose();

        new LoginFrame()
                .setVisible(true);
    }
}