package ui;

import database.LegalDocumentsDAO;
import model.legal_documents;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class LegalDocumentsUI {
    private LegalDocumentsDAO dao;
    private JFrame frame;
    private JTextField caseIdField, documentNameField, documentTypeField, filePathField;
    private JTextArea outputArea;
    
    public LegalDocumentsUI() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_database", "username", "password");
            dao = new LegalDocumentsDAO(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database connection failed!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("Legal Documents Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());
        
        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JLabel("Case ID:"));
        caseIdField = new JTextField();
        panel.add(caseIdField);
        
        panel.add(new JLabel("Document Name:"));
        documentNameField = new JTextField();
        panel.add(documentNameField);
        
        panel.add(new JLabel("Document Type:"));
        documentTypeField = new JTextField();
        panel.add(documentTypeField);
        
        panel.add(new JLabel("File Path:"));
        filePathField = new JTextField();
        panel.add(filePathField);
        
        JButton addButton = new JButton("Add Document");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addDocument();
            }
        });
        panel.add(addButton);
        
        JButton viewButton = new JButton("View All Documents");
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewDocuments();
            }
        });
        panel.add(viewButton);
        
        frame.add(panel, BorderLayout.NORTH);
        
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        frame.add(new JScrollPane(outputArea), BorderLayout.CENTER);
        
        frame.setVisible(true);
    }
    
    private void addDocument() {
        try {
            int caseId = Integer.parseInt(caseIdField.getText());
            String documentName = documentNameField.getText();
            String documentType = documentTypeField.getText();
            String filePath = filePathField.getText();
            
            legal_documents doc = new legal_documents(0, caseId, documentName, documentType, new java.util.Date(), filePath);
            if (dao.addLegalDocument(doc)) {
                JOptionPane.showMessageDialog(frame, "Document added successfully.");
            } else {
                JOptionPane.showMessageDialog(frame, "Failed to add document.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Invalid input!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void viewDocuments() {
        try {
            List<legal_documents> documents = dao.getAllLegalDocuments();
            outputArea.setText("");
            for (legal_documents doc : documents) {
                outputArea.append(doc.getDocumentId() + " - " + doc.getDocumentName() + " (" + doc.getDocumentType() + ")\n");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Error fetching documents.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(LegalDocumentsUI::new);
    }
}