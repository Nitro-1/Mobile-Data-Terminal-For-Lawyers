package ui;

import database.LawyerDAO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import javax.swing.*;

public class LoginUI {
    private JPanel panel;
    private JTextField emailField;
    private JPasswordField barNumberField;
    private LawyerDAO lawyerDAO;
    private Runnable onLoginSuccess;
    private Connection connection;

    public LoginUI(Connection connection) {
        this.connection = connection;
        this.lawyerDAO = new LawyerDAO(connection);
        
        panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel emailPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        emailPanel.add(new JLabel("Email: "));
        emailField = new JTextField(20);
        emailPanel.add(emailField);
        panel.add(emailPanel, BorderLayout.NORTH);

        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        passwordPanel.add(new JLabel("Bar Number: "));
        barNumberField = new JPasswordField(20);
        passwordPanel.add(barNumberField);
        panel.add(passwordPanel, BorderLayout.CENTER);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new LoginActionListener());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loginButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
    }

    public void setOnLoginSuccess(Runnable onLoginSuccess) {
        this.onLoginSuccess = onLoginSuccess;
    }

    private class LoginActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String email = emailField.getText();
            char[] barNumberChars = barNumberField.getPassword();
            String barNumber = new String(barNumberChars);

            try {
                if (lawyerDAO.authenticateLawyer(email, barNumber)) {
                    JOptionPane.showMessageDialog(null, "Login Successful!");

                    if (onLoginSuccess != null) {
                        onLoginSuccess.run();
                    } else {
                        SwingUtilities.invokeLater(() -> {
                            new DOJDashboard(connection).setVisible(true);
                        });
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid credentials!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Login error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                java.util.Arrays.fill(barNumberChars, ' ');
                barNumberField.setText("");
            }
        }
    }

    public JPanel getPanel() {
        return panel;
    }
}
