package ui;

import database.ActivityLogsDAO;
import model.ActivityLogs;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class ActivityLogsUI extends JPanel {
    private ActivityLogsDAO ActivityLogsDAO;
    private JTable table;
    private DefaultTableModel tableModel;
    
    public ActivityLogsUI(Connection connection) {
        this.ActivityLogsDAO = new ActivityLogsDAO(connection);
        setLayout(new BorderLayout());
        
        
        tableModel = new DefaultTableModel(new String[]{"Log ID", "User ID", "Action", "Timestamp"}, 0);
        table = new JTable(tableModel);
        loadActivityLogs();
        add(new JScrollPane(table), BorderLayout.CENTER);
        
        
        JPanel buttonPanel = new JPanel();
        JButton refreshButton = new JButton("Refresh Logs");
        buttonPanel.add(refreshButton);
        add(buttonPanel, BorderLayout.SOUTH);
        
        
        refreshButton.addActionListener(e -> loadActivityLogs());
    }
    
    private void loadActivityLogs() {
        tableModel.setRowCount(0);
        try {
            List<ActivityLogs> logs = ActivityLogsDAO.getAllActivityLogs();
            for (ActivityLogs log : logs) {
                tableModel.addRow(new Object[]{log.getLogId(), log.getUserId(), log.getAction(), log.getTimestamp()});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading activity logs: " + e.getMessage()); 
        }
    }
    
    public JPanel getPanel() {
        return this;
    }
}
