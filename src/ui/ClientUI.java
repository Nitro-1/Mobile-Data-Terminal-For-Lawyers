package ui;

import database.ClientDAO;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;
import model.Client; 

public class ClientUI {
    private ClientDAO clientDAO;
    private JPanel mainPanel;
    private DefaultListModel<Client> clientListModel; 
    private JList<Client> clientList; 


    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField addressField;
    private JTextField dateOfBirthField;

    public ClientUI(Connection connection) {
        this.clientDAO = new ClientDAO(connection);
        initializeUI();
    }

    private void initializeUI() {
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addButton = new JButton("Add Client");
        JButton editButton = new JButton("Edit Client");
        JButton deleteButton = new JButton("Delete Client");
        JButton refreshButton = new JButton("Refresh");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        clientListModel = new DefaultListModel<>();
        clientList = new JList<>(clientListModel);
        JScrollPane scrollPane = new JScrollPane(clientList);
        scrollPane.setPreferredSize(new Dimension(400, 300));

        // Create details panel
        JPanel detailsPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Client Details"));

        firstNameField = new JTextField();
        lastNameField = new JTextField();
        emailField = new JTextField();
        phoneField = new JTextField();
        addressField = new JTextField();
        dateOfBirthField = new JTextField();

        detailsPanel.add(new JLabel("First Name:"));
        detailsPanel.add(firstNameField);
        detailsPanel.add(new JLabel("Last Name:"));
        detailsPanel.add(lastNameField);
        detailsPanel.add(new JLabel("Email:"));
        detailsPanel.add(emailField);
        detailsPanel.add(new JLabel("Phone:"));
        detailsPanel.add(phoneField);
        detailsPanel.add(new JLabel("Address:"));
        detailsPanel.add(addressField);
        detailsPanel.add(new JLabel("Date of Birth (YYYY-MM-DD):"));
        detailsPanel.add(dateOfBirthField);

        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.WEST);
        mainPanel.add(detailsPanel, BorderLayout.CENTER);

        addButton.addActionListener(e -> addClient());
        editButton.addActionListener(e -> editClient());
        deleteButton.addActionListener(e -> deleteClient());
        refreshButton.addActionListener(e -> refreshClientList());

        clientList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                populateFieldsFromSelectedClient();
            }
        });

        refreshClientList();
    }

    private void addClient() {
        try {
            Client newClient = new Client( 
                0,
                firstNameField.getText(),
                lastNameField.getText(),
                emailField.getText(),
                phoneField.getText(),
                addressField.getText(),
                java.sql.Date.valueOf(dateOfBirthField.getText())
            );
            if (clientDAO.addClient(newClient)) {
                refreshClientList();
                clearFields();
                JOptionPane.showMessageDialog(mainPanel, "Client added successfully!");
            } else {
                JOptionPane.showMessageDialog(mainPanel, "Failed to add client.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(mainPanel, "Error adding client: " + ex.getMessage());
        }
    }

    private void editClient() {
        Client selectedClient = clientList.getSelectedValue(); 
        if (selectedClient != null) {
            try {
                selectedClient.setFirstName(firstNameField.getText());
                selectedClient.setLastName(lastNameField.getText());
                selectedClient.setEmail(emailField.getText());
                selectedClient.setPhone(phoneField.getText());
                selectedClient.setAddress(addressField.getText());
                selectedClient.setDateOfBirth(java.sql.Date.valueOf(dateOfBirthField.getText()));

                if (clientDAO.updateClient(selectedClient)) {
                    refreshClientList();
                    clearFields();
                    JOptionPane.showMessageDialog(mainPanel, "Client updated successfully!");
                } else {
                    JOptionPane.showMessageDialog(mainPanel, "Failed to update client.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(mainPanel, "Error updating client: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(mainPanel, "Please select a client to edit");
        }
    }

    private void deleteClient() {
        Client selectedClient = clientList.getSelectedValue(); 
        if (selectedClient != null) {
            int confirm = JOptionPane.showConfirmDialog(mainPanel, "Are you sure you want to delete this client?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    if (clientDAO.deleteClient(selectedClient.getClientId())) {
                        refreshClientList();
                        clearFields();
                        JOptionPane.showMessageDialog(mainPanel, "Client deleted successfully!");
                    } else {
                        JOptionPane.showMessageDialog(mainPanel, "Failed to delete client.");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(mainPanel, "Error deleting client: " + ex.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(mainPanel, "Please select a client to delete");
        }
    }

    private void refreshClientList() {
        clientListModel.clear();
        try {
            List<Client> clients = clientDAO.getAllClients(); 
            for (Client c : clients) {
                clientListModel.addElement(c);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(mainPanel, "Error loading clients: " + e.getMessage());
        }
    }

    private void populateFieldsFromSelectedClient() {
        Client selectedClient = clientList.getSelectedValue(); 
        if (selectedClient != null) {
            firstNameField.setText(selectedClient.getFirstName());
            lastNameField.setText(selectedClient.getLastName());
            emailField.setText(selectedClient.getEmail());
            phoneField.setText(selectedClient.getPhone());
            addressField.setText(selectedClient.getAddress());
            dateOfBirthField.setText(selectedClient.getDateOfBirth().toString());
        }
    }

    public JPanel getPanel() {
        return mainPanel;
    }

    private void clearFields() {
        firstNameField.setText("");
        lastNameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        addressField.setText("");
        dateOfBirthField.setText("");
    }
}
