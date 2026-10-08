package gui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class UIStyle {

    // =========================================================
    // COLORS
    // =========================================================

    // Main red color.
    public static final Color RED =
            new Color(128, 0, 0);

    // Darker red.
    public static final Color DARK_RED =
            new Color(88, 0, 0);

    // Main gold color.
    public static final Color GOLD =
            new Color(212, 175, 55);

    // Softer gold.
    public static final Color LIGHT_GOLD =
            new Color(245, 231, 166);

    // Main application background.
    public static final Color CREAM =
            new Color(250, 248, 240);

    // White.
    public static final Color WHITE =
            Color.WHITE;

    // Main text color.
    public static final Color DARK_TEXT =
            new Color(45, 45, 45);

    // Gray text.
    public static final Color GRAY =
            new Color(110, 110, 110);

    // Success color.
    public static final Color GREEN =
            new Color(46, 125, 50);

    // Warning color.
    public static final Color ORANGE =
            new Color(230, 126, 34);


    // =========================================================
    // FONTS
    // =========================================================

    // Main title.
    public static final Font TITLE_FONT =
            new Font(
                    "Segoe UI",
                    Font.BOLD,
                    26
            );

    // Section headers.
    public static final Font HEADER_FONT =
            new Font(
                    "Segoe UI",
                    Font.BOLD,
                    20
            );

    // Normal text.
    public static final Font NORMAL_FONT =
            new Font(
                    "Segoe UI",
                    Font.PLAIN,
                    14
            );

    // Labels.
    public static final Font LABEL_FONT =
            new Font(
                    "Segoe UI",
                    Font.BOLD,
                    14
            );

    // Small text.
    public static final Font SMALL_FONT =
            new Font(
                    "Segoe UI",
                    Font.PLAIN,
                    12
            );


    // =========================================================
    // FRAME STYLE
    // =========================================================

    public static void setupFrame(JFrame frame) {

        // Default window size.
        frame.setSize(900, 600);

        // Center the window.
        frame.setLocationRelativeTo(null);

        // Prevent resizing.
        frame.setResizable(false);

        // Cream background.
        frame.getContentPane()
                .setBackground(CREAM);

        // Close application when X is clicked.
        frame.setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );
    }


    // =========================================================
    // CARD
    // =========================================================

    public static JPanel styleCard() {

        // Create a new panel.
        JPanel card = new JPanel();

        // White card background.
        card.setBackground(WHITE);

        // Create gold border.
        Border border =
                BorderFactory.createCompoundBorder(

                        // Outer gold border.
                        BorderFactory.createLineBorder(
                                LIGHT_GOLD,
                                2
                        ),

                        // Inner padding.
                        BorderFactory.createEmptyBorder(
                                20,
                                20,
                                20,
                                20
                        )
                );

        // Apply border.
        card.setBorder(border);

        return card;
    }


    // =========================================================
    // LABEL
    // =========================================================

    public static JLabel styleLabel(String text) {

        // Create label using the provided text.
        JLabel label =
                new JLabel(text);

        // Set font.
        label.setFont(LABEL_FONT);

        // Set text color.
        label.setForeground(DARK_TEXT);

        return label;
    }


    // =========================================================
    // TEXT FIELD
    // =========================================================

    public static JTextField styleTextField() {

        // Create text field.
        JTextField field =
                new JTextField();

        // Set font.
        field.setFont(NORMAL_FONT);

        // White background.
        field.setBackground(WHITE);

        // Dark text.
        field.setForeground(DARK_TEXT);

        // Gold border.
        field.setBorder(
                BorderFactory.createCompoundBorder(

                        BorderFactory.createLineBorder(
                                GOLD,
                                2
                        ),

                        BorderFactory.createEmptyBorder(
                                5,
                                10,
                                5,
                                10
                        )
                )
        );

        // Size of the text field.
        field.setPreferredSize(
                new Dimension(280, 40)
        );

        return field;
    }


    // =========================================================
    // PASSWORD FIELD
    // =========================================================

    public static JPasswordField stylePasswordField() {

        // Password field hides the characters.
        JPasswordField field =
                new JPasswordField();

        field.setFont(NORMAL_FONT);

        field.setBackground(WHITE);

        field.setForeground(DARK_TEXT);

        field.setBorder(
                BorderFactory.createCompoundBorder(

                        BorderFactory.createLineBorder(
                                GOLD,
                                2
                        ),

                        BorderFactory.createEmptyBorder(
                                5,
                                10,
                                5,
                                10
                        )
                )
        );

        field.setPreferredSize(
                new Dimension(280, 40)
        );

        return field;
    }


    // =========================================================
    // GOLD BUTTON
    // =========================================================

    public static JButton styleGoldButton(String text) {

        // Create button.
        JButton button =
                new JButton(text);

        // Gold background.
        button.setBackground(GOLD);

        // Dark red text.
        button.setForeground(DARK_RED);

        // Bold font.
        button.setFont(LABEL_FONT);

        // Remove focus outline.
        button.setFocusPainted(false);

        // Remove default border.
        button.setBorderPainted(false);

        // Button size.
        button.setPreferredSize(
                new Dimension(180, 42)
        );

        // Hand cursor.
        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        return button;
    }


    // =========================================================
    // RED BUTTON
    // =========================================================

    public static JButton styleRedButton(String text) {

        JButton button =
                new JButton(text);

        button.setBackground(RED);

        button.setForeground(GOLD);

        button.setFont(LABEL_FONT);

        button.setFocusPainted(false);

        button.setBorderPainted(false);

        button.setPreferredSize(
                new Dimension(180, 42)
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        return button;
    }


    // =========================================================
    // STANDARD BUTTON
    // =========================================================

    public static JButton styleButton(String text) {

        return styleRedButton(text);
    }


    // =========================================================
    // TABLE STYLE
    // =========================================================

    public static void styleTable(JTable table) {

        // Table font.
        table.setFont(NORMAL_FONT);

        // Row height.
        table.setRowHeight(32);

        // White background.
        table.setBackground(WHITE);

        // Dark text.
        table.setForeground(DARK_TEXT);

        // Selected row background.
        table.setSelectionBackground(
                LIGHT_GOLD
        );

        // Selected row text.
        table.setSelectionForeground(
                DARK_TEXT
        );

        // Grid color.
        table.setGridColor(
                LIGHT_GOLD
        );


        // Table header.
        JTableHeader header =
                table.getTableHeader();

        // Red header.
        header.setBackground(RED);

        // Gold header text.
        header.setForeground(GOLD);

        // Bold header.
        header.setFont(LABEL_FONT);

        // Header height.
        header.setPreferredSize(
                new Dimension(0, 38)
        );
    }


    // =========================================================
    // COMBO BOX
    // =========================================================

    public static JComboBox<?> styleComboBox(
            JComboBox<?> comboBox) {

        comboBox.setFont(NORMAL_FONT);

        comboBox.setBackground(WHITE);

        comboBox.setForeground(DARK_TEXT);

        comboBox.setPreferredSize(
                new Dimension(280, 40)
        );

        return comboBox;
    }


    // =========================================================
    // TEXT AREA
    // =========================================================

    public static JTextArea styleTextArea() {

        JTextArea area =
                new JTextArea();

        area.setFont(NORMAL_FONT);

        area.setBackground(WHITE);

        area.setForeground(DARK_TEXT);

        area.setLineWrap(true);

        area.setWrapStyleWord(true);

        area.setBorder(
                BorderFactory.createCompoundBorder(

                        BorderFactory.createLineBorder(
                                GOLD,
                                2
                        ),

                        BorderFactory.createEmptyBorder(
                                8,
                                10,
                                8,
                                10
                        )
                )
        );

        return area;
    }


    // =========================================================
    // SUCCESS MESSAGE
    // =========================================================

    public static void showSuccess(
            Component parent,
            String message) {

        JOptionPane.showMessageDialog(
                parent,
                message,
                "Success",
                JOptionPane.INFORMATION_MESSAGE
        );
    }


    // =========================================================
    // WARNING MESSAGE
    // =========================================================

    public static void showWarning(
            Component parent,
            String message) {

        JOptionPane.showMessageDialog(
                parent,
                message,
                "Warning",
                JOptionPane.WARNING_MESSAGE
        );
    }


    // =========================================================
    // ERROR MESSAGE
    // =========================================================

    public static void showError(
            Component parent,
            String message) {

        JOptionPane.showMessageDialog(
                parent,
                message,
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}