package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import database.CourtHearingsDAO;
import model.CourtHearings;

public class CourtHearingsUI extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private CourtHearingsDAO dao;

    public CourtHearingsUI(Connection connection) {
        dao = new CourtHearingsDAO(connection);
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new String[]{"ID", "Case ID", "Date", "Court", "Judge", "Status"}, 0);
        table = new JTable(tableModel);
        loadHearings();
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel panel = new JPanel();
        JButton addButton = new JButton("Add Hearing");
        JButton deleteButton = new JButton("Delete Hearing");
        panel.add(addButton);
        panel.add(deleteButton);
        add(panel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addHearing());
        deleteButton.addActionListener(e -> deleteHearing());
    }

    private void loadHearings() {
        tableModel.setRowCount(0);
        try {
            List<CourtHearings> hearings = dao.getAllCourtHearings();
            for (CourtHearings h : hearings) {
                tableModel.addRow(new Object[]{
                    h.getHearingId(), 
                    h.getCaseId(), 
                    h.getHearingDate(), 
                    h.getCourtName(), 
                    h.getJudgeName(), 
                    h.getHearingStatus()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading hearings: " + e.getMessage());
        }
    }

    private void addHearing() {
        JTextField caseIdField = new JTextField();
        JTextField dateField = new JTextField();
        JTextField courtNameField = new JTextField();
        JTextField judgeNameField = new JTextField();
        JTextField statusField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JLabel("Case ID:"));
        panel.add(caseIdField);
        panel.add(new JLabel("Date (YYYY-MM-DD):"));
        panel.add(dateField);
        panel.add(new JLabel("Court Name:"));
        panel.add(courtNameField);
        panel.add(new JLabel("Judge Name:"));
        panel.add(judgeNameField);
        panel.add(new JLabel("Status:"));
        panel.add(statusField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add Hearing", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                if (caseIdField.getText().trim().isEmpty() || dateField.getText().trim().isEmpty() ||
                        courtNameField.getText().trim().isEmpty() || judgeNameField.getText().trim().isEmpty() ||
                        statusField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "All fields must be filled!");
                    return;
                }

                int caseId = Integer.parseInt(caseIdField.getText().trim());
                java.sql.Date hearingDate = java.sql.Date.valueOf(dateField.getText().trim());
                String courtName = courtNameField.getText().trim();
                String judgeName = judgeNameField.getText().trim();
                String status = statusField.getText().trim();

                CourtHearings hearing = new CourtHearings(0, caseId, hearingDate, courtName, judgeName, status);
                boolean success = dao.addCourtHearing(hearing);
                
                if (success) {
                    loadHearings();
                    JOptionPane.showMessageDialog(this, "Hearing added successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add hearing.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid case ID. Please enter a valid number.");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, "Invalid date format. Use YYYY-MM-DD.");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error adding hearing: " + ex.getMessage());
            }
        }
    }

    private void deleteHearing() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int hearingId = (int) tableModel.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this hearing?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    boolean success = dao.deleteCourtHearing(hearingId);
                    if (success) {
                        loadHearings();
                        JOptionPane.showMessageDialog(this, "Hearing deleted successfully!");
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to delete hearing.");
                    }
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "Error deleting hearing: " + e.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a hearing to delete.");
        }
    }

    public JPanel getPanel() {
        return this;
    }
}
