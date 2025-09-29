package ui;

import database.CasesDAO;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;
import model.Cases;

public class CasesUI {
    private CasesDAO casesDAO;
    private JPanel mainPanel;
    private DefaultListModel<Cases> caseListModel;
    private JList<Cases> caseList;
    
    private JTextField caseNumberField;
    private JTextField caseTypeField;
    private JTextField statusField;
    private JTextField filingDateField;
    private JTextField closingDateField;
    private JTextField assignedLawyerField;

    public CasesUI(Connection connection) {
        this.casesDAO = new CasesDAO(connection);
        initializeUI();
    }

    private void initializeUI() {
        mainPanel = new JPanel(new BorderLayout(50, 50));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addButton = new JButton("Add Case");
        JButton editButton = new JButton("Edit Case");
        JButton deleteButton = new JButton("Delete Case");
        JButton refreshButton = new JButton("Refresh");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        caseListModel = new DefaultListModel<>();
        caseList = new JList<>(caseListModel);
        JScrollPane scrollPane = new JScrollPane(caseList);
        scrollPane.setPreferredSize(new Dimension(400, 300));

        JPanel detailsPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Case Details"));
        
        caseNumberField = new JTextField();
        caseTypeField = new JTextField();
        statusField = new JTextField();
        filingDateField = new JTextField();
        closingDateField = new JTextField();
        assignedLawyerField = new JTextField();

        detailsPanel.add(new JLabel("Case Number:"));
        detailsPanel.add(caseNumberField);
        detailsPanel.add(new JLabel("Case Type:"));
        detailsPanel.add(caseTypeField);
        detailsPanel.add(new JLabel("Status:"));
        detailsPanel.add(statusField);
        detailsPanel.add(new JLabel("Filing Date (YYYY-MM-DD):"));
        detailsPanel.add(filingDateField);
        detailsPanel.add(new JLabel("Closing Date (YYYY-MM-DD):"));
        detailsPanel.add(closingDateField);
        detailsPanel.add(new JLabel("Assigned Lawyer ID:"));
        detailsPanel.add(assignedLawyerField);

        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.WEST);
        mainPanel.add(detailsPanel, BorderLayout.CENTER);

        addButton.addActionListener(e -> addCase());
        editButton.addActionListener(e -> editCase());
        deleteButton.addActionListener(e -> deleteCase());
        refreshButton.addActionListener(e -> refreshCaseList());

        caseList.addListSelectionListener(e -> populateFieldsFromSelectedCase());

        refreshCaseList();
    }

    private void addCase() {
        try {
            Cases newCase = new Cases(
                0,
                caseNumberField.getText().trim(),
                caseTypeField.getText().trim(),
                statusField.getText().trim(),
                java.sql.Date.valueOf(filingDateField.getText().trim()),
                closingDateField.getText().trim().isEmpty() ? null : java.sql.Date.valueOf(closingDateField.getText().trim()),
                Integer.parseInt(assignedLawyerField.getText().trim())
            );
            
            if (casesDAO.addCase(newCase)) {
                refreshCaseList();
                clearFields();
                JOptionPane.showMessageDialog(mainPanel, "Case added successfully!");
            } else {
                JOptionPane.showMessageDialog(mainPanel, 
                    "Failed to add case. Please check your input.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(mainPanel, 
                "Invalid date format. Please use YYYY-MM-DD format.", 
                "Input Error", 
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(mainPanel, 
                "Error adding case: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editCase() {
        Cases selectedCase = caseList.getSelectedValue();
        if (selectedCase != null) {
            try {
                selectedCase.setCaseNumber(caseNumberField.getText().trim());
                selectedCase.setCaseType(caseTypeField.getText().trim());
                selectedCase.setStatus(statusField.getText().trim());
                selectedCase.setFilingDate(java.sql.Date.valueOf(filingDateField.getText().trim()));
                selectedCase.setClosingDate(closingDateField.getText().trim().isEmpty() ? 
                    null : java.sql.Date.valueOf(closingDateField.getText().trim()));
                selectedCase.setAssignedLawyer(Integer.parseInt(assignedLawyerField.getText().trim()));
                
                if (casesDAO.updateCase(selectedCase)) {
                    refreshCaseList();
                    clearFields();
                    JOptionPane.showMessageDialog(mainPanel, "Case updated successfully!");
                } else {
                    JOptionPane.showMessageDialog(mainPanel, 
                        "Failed to update case. Please check your input.", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(mainPanel, 
                    "Invalid date format. Please use YYYY-MM-DD format.", 
                    "Input Error", 
                    JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(mainPanel, 
                    "Error updating case: " + ex.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(mainPanel, "Please select a case to edit");
        }
    }

    private void deleteCase() {
        Cases selectedCase = caseList.getSelectedValue();
        if (selectedCase != null) {
            int confirm = JOptionPane.showConfirmDialog(mainPanel, 
                "Are you sure you want to delete this case?", 
                "Confirm Delete", 
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
                
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    if (casesDAO.deleteCase(selectedCase.getCaseId())) {
                        refreshCaseList();
                        clearFields();
                        JOptionPane.showMessageDialog(mainPanel, "Case deleted successfully!");
                    } else {
                        JOptionPane.showMessageDialog(mainPanel, 
                            "Failed to delete case. It may be referenced by other records.", 
                            "Error", 
                            JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(mainPanel, 
                        "Error deleting case: " + ex.getMessage(), 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(mainPanel, "Please select a case to delete");
        }
    }

    private void refreshCaseList() {
        caseListModel.clear();
        try {
            List<Cases> caseListData = casesDAO.getAllCases();
            for (Cases caseObj : caseListData) {
                caseListModel.addElement(caseObj);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(mainPanel, "Error loading cases: " + e.getMessage());
        }
    }

    private void populateFieldsFromSelectedCase() {
        Cases selectedCase = caseList.getSelectedValue();
        if (selectedCase != null) {
            caseNumberField.setText(selectedCase.getCaseNumber());
            caseTypeField.setText(selectedCase.getCaseType());
            statusField.setText(selectedCase.getStatus());
            filingDateField.setText(selectedCase.getFilingDate().toString());
            closingDateField.setText(selectedCase.getClosingDate() != null ? selectedCase.getClosingDate().toString() : "");
            assignedLawyerField.setText(String.valueOf(selectedCase.getAssignedLawyer()));
        }
    }

    public JPanel getPanel() {
        return mainPanel;
    }

    private void clearFields() {
        caseNumberField.setText("");
        caseTypeField.setText("");
        statusField.setText("");
        filingDateField.setText("");
        closingDateField.setText("");
        assignedLawyerField.setText("");
    }
}
