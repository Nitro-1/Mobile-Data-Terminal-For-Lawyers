package database;

import model.legal_documents;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LegalDocumentsDAO {
    private Connection connection;

    public LegalDocumentsDAO(Connection connection) {
        this.connection = connection;
    }

    
    public boolean addLegalDocument(legal_documents document) throws SQLException {
        String query = "INSERT INTO Legal_Documents (case_id, document_name, document_type, upload_date, file_path) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, document.getCaseId());
            stmt.setString(2, document.getDocumentName());
            stmt.setString(3, document.getDocumentType());
            stmt.setTimestamp(4, new Timestamp(document.getUploadDate().getTime()));
            stmt.setString(5, document.getFilePath());
            return stmt.executeUpdate() > 0;
        }
    }

    
    public legal_documents getLegalDocumentById(int documentId) throws SQLException {
        String query = "SELECT * FROM Legal_Documents WHERE document_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, documentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new legal_documents(
                    rs.getInt("document_id"),
                    rs.getInt("case_id"),
                    rs.getString("document_name"),
                    rs.getString("document_type"),
                    rs.getTimestamp("upload_date"),
                    rs.getString("file_path")
                );
            }
        }
        return null;
    }

    
    public List<legal_documents> getAllLegalDocuments() throws SQLException {
        List<legal_documents> documents = new ArrayList<>();
        String query = "SELECT * FROM Legal_Documents";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                documents.add(new legal_documents(
                    rs.getInt("document_id"),
                    rs.getInt("case_id"),
                    rs.getString("document_name"),
                    rs.getString("document_type"),
                    rs.getTimestamp("upload_date"),
                    rs.getString("file_path")
                ));
            }
        }
        return documents;
    }

    
    public boolean updateLegalDocument(legal_documents document) throws SQLException {
        String query = "UPDATE Legal_Documents SET case_id = ?, document_name = ?, document_type = ?, upload_date = ?, file_path = ? WHERE document_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, document.getCaseId());
            stmt.setString(2, document.getDocumentName());
            stmt.setString(3, document.getDocumentType());
            stmt.setTimestamp(4, new Timestamp(document.getUploadDate().getTime()));
            stmt.setString(5, document.getFilePath());
            stmt.setInt(6, document.getDocumentId());
            return stmt.executeUpdate() > 0;
        }
    }

    
    public boolean deleteLegalDocument(int documentId) throws SQLException {
        String query = "DELETE FROM Legal_Documents WHERE document_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, documentId);
            return stmt.executeUpdate() > 0;
        }
    }
}