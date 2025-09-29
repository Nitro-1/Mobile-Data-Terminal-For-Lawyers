package main;

import database.DatabaseManager;
import ui.LoginUI;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Failed to set look and feel: " + e.getMessage());
        }

        
        Connection connection = DatabaseManager.getConnection();
        if (connection == null) {
            JOptionPane.showMessageDialog(null, "Database connection failed!", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("DOJ Mobile Data Terminal");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null); 

            
            frame.setContentPane(new LoginUI(connection).getPanel());

            frame.setVisible(true);
        });
    }
}
