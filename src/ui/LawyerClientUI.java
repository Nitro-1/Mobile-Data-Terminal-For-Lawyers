package ui;

import database.LawyerClientDAO;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.lawyer_client;

public class LawyerClientUI {
    private JPanel mainPanel;
    private JTable table;
    private DefaultTableModel tableModel;
    private LawyerClientDAO lawyerClientDAO;
    
    public LawyerClientUI(Connection connection) {
        this.lawyerClientDAO = new LawyerClientDAO(connection);
        initializeUI();
    }

    private void initializeUI() {
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        tableModel = new DefaultTableModel(new String[]{"ID", "Lawyer ID", "Client ID", "Case ID"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;  // Make table read-only
            }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addButton = new JButton("Add Assignment");
        JButton deleteButton = new JButton("Delete Assignment");
        JButton refreshButton = new JButton("Refresh");
        
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        
        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        
        addButton.addActionListener(e -> addLawyerClient());
        deleteButton.addActionListener(e -> deleteLawyerClient());
        refreshButton.addActionListener(e -> refreshTable());
        
        refreshTable();
    }
    
    private void refreshTable() {
        tableModel.setRowCount(0);
        try {
            List<lawyer_client> list = lawyerClientDAO.getAllLawyerClients();
            for (lawyer_client lc : list) {
                tableModel.addRow(new Object[]{
                    lc.getId(), 
                    lc.getLawyerId(), 
                    lc.getClientId(), 
                    lc.getCaseId()
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(mainPanel, 
                "Error loading data: " + e.getMessage(), 
                "Database Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void addLawyerClient() {
        JTextField lawyerIdField = new JTextField(10);
        JTextField clientIdField = new JTextField(10);
        JTextField caseIdField = new JTextField(10);
        
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        inputPanel.add(new JLabel("Lawyer ID:"));
        inputPanel.add(lawyerIdField);
        inputPanel.add(new JLabel("Client ID:"));
        inputPanel.add(clientIdField);
        inputPanel.add(new JLabel("Case ID:"));
        inputPanel.add(caseIdField);
        
        int option = JOptionPane.showConfirmDialog(mainPanel, 
            inputPanel, 
            "Add Lawyer-Client Assignment", 
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE);
            
        if (option == JOptionPane.OK_OPTION) {
            try {
                int lawyerId = Integer.parseInt(lawyerIdField.getText().trim());
                int clientId = Integer.parseInt(clientIdField.getText().trim());
                int caseId = Integer.parseInt(caseIdField.getText().trim());
                
                lawyer_client lc = new lawyer_client(0, lawyerId, clientId, caseId);
                if (lawyerClientDAO.insertLawyerClient(lc)) {
                    refreshTable();
                    JOptionPane.showMessageDialog(mainPanel, 
                        "Assignment added successfully.", 
                        "Success", 
                        JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(mainPanel, 
                    "Please enter valid numbers for all fields.", 
                    "Input Error", 
                    JOptionPane.ERROR_MESSAGE);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(mainPanel, 
                    "Error adding assignment: " + e.getMessage(), 
                    "Database Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void deleteLawyerClient() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(mainPanel, 
                "Please select an assignment to delete.", 
                "Selection Required", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int id = (int) tableModel.getValueAt(selectedRow, 0);
        int option = JOptionPane.showConfirmDialog(mainPanel, 
            "Are you sure you want to delete this assignment?", 
            "Confirm Delete", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
            
        if (option == JOptionPane.YES_OPTION) {
            try {
                if (lawyerClientDAO.deleteLawyerClient(id)) {
                    refreshTable();
                    JOptionPane.showMessageDialog(mainPanel, 
                        "Assignment deleted successfully.", 
                        "Success", 
                        JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(mainPanel, 
                    "Error deleting assignment: " + e.getMessage(), 
                    "Database Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public JPanel getPanel() {
        return mainPanel;
    }
}