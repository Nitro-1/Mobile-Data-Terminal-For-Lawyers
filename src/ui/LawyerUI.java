package ui;

import database.LawyerDAO;
import model.Lawyer;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LawyerUI {
    private LawyerDAO lawyerDAO;
    private JPanel mainPanel;
    private DefaultListModel<Lawyer> lawyerListModel;
    private JList<Lawyer> lawyerList;

    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField barNumberField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField specializationField;
    private JTextField yearsField;
    private JTextField statusField;

    public LawyerUI(Connection connection) {
        this.lawyerDAO = new LawyerDAO(connection);
        initializeUI();
    }

    private void initializeUI() {
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addButton = new JButton("Add Lawyer");
        JButton editButton = new JButton("Edit Lawyer");
        JButton deleteButton = new JButton("Delete Lawyer");
        JButton refreshButton = new JButton("Refresh");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        lawyerListModel = new DefaultListModel<>();
        lawyerList = new JList<>(lawyerListModel);
        JScrollPane scrollPane = new JScrollPane(lawyerList);
        scrollPane.setPreferredSize(new Dimension(400, 300));

        JPanel detailsPanel = new JPanel(new GridLayout(8, 2, 5, 5));
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Lawyer Details"));

        firstNameField = new JTextField();
        lastNameField = new JTextField();
        barNumberField = new JTextField();
        emailField = new JTextField();
        phoneField = new JTextField();
        specializationField = new JTextField();
        yearsField = new JTextField();
        statusField = new JTextField();

        detailsPanel.add(new JLabel("First Name:"));
        detailsPanel.add(firstNameField);
        detailsPanel.add(new JLabel("Last Name:"));
        detailsPanel.add(lastNameField);
        detailsPanel.add(new JLabel("Bar Number:"));
        detailsPanel.add(barNumberField);
        detailsPanel.add(new JLabel("Email:"));
        detailsPanel.add(emailField);
        detailsPanel.add(new JLabel("Phone:"));
        detailsPanel.add(phoneField);
        detailsPanel.add(new JLabel("Specialization:"));
        detailsPanel.add(specializationField);
        detailsPanel.add(new JLabel("Years of Experience:"));
        detailsPanel.add(yearsField);
        detailsPanel.add(new JLabel("Status:"));
        detailsPanel.add(statusField);

        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.WEST);
        mainPanel.add(detailsPanel, BorderLayout.CENTER);

        addButton.addActionListener(e -> addLawyer());
        editButton.addActionListener(e -> editLawyer());
        deleteButton.addActionListener(e -> deleteLawyer());
        refreshButton.addActionListener(e -> refreshLawyerList());

        lawyerList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateFieldsWithSelectedLawyer();
            }
        });

        refreshLawyerList();
    }

    private void addLawyer() {
        try {
            int years = validateYearsInput(yearsField.getText());
            if (years == -1) {
                JOptionPane.showMessageDialog(mainPanel, "Invalid Years of Experience! Please enter a valid number.");
                return;
            }

            Lawyer lawyer = new Lawyer(
                0,
                firstNameField.getText(),
                lastNameField.getText(),
                barNumberField.getText(),
                emailField.getText(),
                phoneField.getText(),
                specializationField.getText(),
                years,
                statusField.getText()
            );

            if (lawyerDAO.addLawyer(lawyer)) {
                refreshLawyerList();
                clearFields();
                JOptionPane.showMessageDialog(mainPanel, "Lawyer added successfully!");
            } else {
                JOptionPane.showMessageDialog(mainPanel, "Failed to add lawyer.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(mainPanel, "Error adding lawyer: " + ex.getMessage());
        }
    }

    private void editLawyer() {
        Lawyer selectedLawyer = lawyerList.getSelectedValue();
        if (selectedLawyer != null) {
            try {
                int years = validateYearsInput(yearsField.getText());
                if (years == -1) {
                    JOptionPane.showMessageDialog(mainPanel, "Invalid Years of Experience! Please enter a valid number.");
                    return;
                }

                selectedLawyer.setFirstName(firstNameField.getText());
                selectedLawyer.setLastName(lastNameField.getText());
                selectedLawyer.setBarNumber(barNumberField.getText());
                selectedLawyer.setEmail(emailField.getText());
                selectedLawyer.setPhone(phoneField.getText());
                selectedLawyer.setSpecialization(specializationField.getText());
                selectedLawyer.setYearsOfExperience(years);
                selectedLawyer.setStatus(statusField.getText());

                if (lawyerDAO.updateLawyer(selectedLawyer)) {
                    refreshLawyerList();
                    clearFields();
                    JOptionPane.showMessageDialog(mainPanel, "Lawyer updated successfully!");
                } else {
                    JOptionPane.showMessageDialog(mainPanel, "Failed to update lawyer.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(mainPanel, "Error updating lawyer: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(mainPanel, "Please select a lawyer to edit.");
        }
    }

    private void deleteLawyer() {
        Lawyer selectedLawyer = lawyerList.getSelectedValue();
        if (selectedLawyer != null) {
            int confirm = JOptionPane.showConfirmDialog(mainPanel, "Are you sure you want to delete this lawyer?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    if (lawyerDAO.deleteLawyer(selectedLawyer.getLawyerId())) {
                        refreshLawyerList();
                        clearFields();
                        JOptionPane.showMessageDialog(mainPanel, "Lawyer deleted successfully!");
                    } else {
                        JOptionPane.showMessageDialog(mainPanel, "Failed to delete lawyer.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(mainPanel, "Error deleting lawyer: " + ex.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(mainPanel, "Please select a lawyer to delete.");
        }
    }

    private void updateFieldsWithSelectedLawyer() {
        Lawyer selectedLawyer = lawyerList.getSelectedValue();
        if (selectedLawyer != null) {
            firstNameField.setText(selectedLawyer.getFirstName());
            lastNameField.setText(selectedLawyer.getLastName());
            barNumberField.setText(selectedLawyer.getBarNumber());
            emailField.setText(selectedLawyer.getEmail());
            phoneField.setText(selectedLawyer.getPhone());
            specializationField.setText(selectedLawyer.getSpecialization());
            yearsField.setText(String.valueOf(selectedLawyer.getYearsOfExperience()));
            statusField.setText(selectedLawyer.getStatus());
        }
    }

    private void refreshLawyerList() {
        try {
            lawyerListModel.clear();
            List<Lawyer> lawyers = lawyerDAO.getAllLawyers();
            for (Lawyer lawyer : lawyers) {
                lawyerListModel.addElement(lawyer);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(mainPanel, "Error loading lawyers: " + e.getMessage());
        }
    }

    private int validateYearsInput(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public JPanel getPanel() {
        return mainPanel;
    }

    private void clearFields() {
        firstNameField.setText("");
        lastNameField.setText("");
        barNumberField.setText("");
        emailField.setText("");
        phoneField.setText("");
        specializationField.setText("");
        yearsField.setText("");
        statusField.setText("");
    }
}
