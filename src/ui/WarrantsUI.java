package ui;

import database.WarrantsDAO;
import model.Warrants;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;

public class WarrantsUI {
    private WarrantsDAO WarrantsDAO;
    private JFrame frame;
    private JTextField caseIdField, warrantTypeField, issuingJudgeField, statusField, detailsField;
    private JButton addButton, fetchButton;
    
    public WarrantsUI(Connection connection) {
        this.WarrantsDAO = new WarrantsDAO(connection);
        initializeUI();
    }
    
    private void initializeUI() {
        frame = new JFrame("Warrant Management");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(7, 2));

        frame.add(new JLabel("Case ID:"));
        caseIdField = new JTextField();
        frame.add(caseIdField);
        
        frame.add(new JLabel("Warrant Type:"));
        warrantTypeField = new JTextField();
        frame.add(warrantTypeField);
        
        frame.add(new JLabel("Issuing Judge:"));
        issuingJudgeField = new JTextField();
        frame.add(issuingJudgeField);
        
        frame.add(new JLabel("Status:"));
        statusField = new JTextField();
        frame.add(statusField);
        
        frame.add(new JLabel("Details:"));
        detailsField = new JTextField();
        frame.add(detailsField);
        
        addButton = new JButton("Add Warrant");
        fetchButton = new JButton("Fetch Warrant");
        frame.add(addButton);
        frame.add(fetchButton);
        
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addWarrant();
            }
        });
        
        fetchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fetchWarrant();
            }
        });
        
        frame.setVisible(true);
    }
    
    private void addWarrant() {
        try {
            int caseId = Integer.parseInt(caseIdField.getText());
            String warrantType = warrantTypeField.getText();
            String issuingJudge = issuingJudgeField.getText();
            String status = statusField.getText();
            String details = detailsField.getText();
            
            Warrants warrant = new Warrants(0, caseId, warrantType, new Date(), null, issuingJudge, status, details);
            if (WarrantsDAO.insertWarrant(warrant)) {
                JOptionPane.showMessageDialog(frame, "Warrant added successfully!");
            } else {
                JOptionPane.showMessageDialog(frame, "Failed to add warrant.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
        }
    }
    
    private void fetchWarrant() {
        try {
            int warrantId = Integer.parseInt(JOptionPane.showInputDialog("Enter Warrant ID:"));
            Warrants warrant = WarrantsDAO.getWarrantById(warrantId);
            
            if (warrant != null) {
                JOptionPane.showMessageDialog(frame, "Warrant Found:\n" + warrant.toString());
            } else {
                JOptionPane.showMessageDialog(frame, "No warrant found with ID: " + warrantId);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
        }
    }
    
    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/yourdatabase", "user", "password");
            new WarrantsUI(connection);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
