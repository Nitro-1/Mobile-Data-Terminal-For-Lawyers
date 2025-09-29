package database;

import model.Cases;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CasesDAO extends BaseDAO {

    public CasesDAO(Connection connection) {
        super(connection); 
    }

    public boolean addCase(Cases caseObj) throws SQLException {
        ensureConnection(); 
        String query = "INSERT INTO Cases (case_number, case_type, status, filing_date, closing_date, assigned_lawyer) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, caseObj.getCaseNumber());
            stmt.setString(2, caseObj.getCaseType());
            stmt.setString(3, caseObj.getStatus());
            stmt.setDate(4, new java.sql.Date(caseObj.getFilingDate().getTime()));
            stmt.setDate(5, caseObj.getClosingDate() != null ? new java.sql.Date(caseObj.getClosingDate().getTime()) : null);
            stmt.setInt(6, caseObj.getAssignedLawyer());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        caseObj.setCaseId(generatedKeys.getInt(1));
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public Cases getCaseById(int caseId) throws SQLException {
        ensureConnection(); 
        String query = "SELECT * FROM Cases WHERE case_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, caseId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Cases(
                    rs.getInt("case_id"),
                    rs.getString("case_number"),
                    rs.getString("case_type"),
                    rs.getString("status"),
                    rs.getDate("filing_date"),
                    rs.getDate("closing_date"),
                    rs.getInt("assigned_lawyer")
                );
            }
        }
        return null;
    }

    public List<Cases> getAllCases() throws SQLException {
        ensureConnection(); 
        List<Cases> caseList = new ArrayList<>();
        String query = "SELECT * FROM Cases";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                caseList.add(new Cases(
                    rs.getInt("case_id"),
                    rs.getString("case_number"),
                    rs.getString("case_type"),
                    rs.getString("status"),
                    rs.getDate("filing_date"),
                    rs.getDate("closing_date"),
                    rs.getInt("assigned_lawyer")
                ));
            }
        }
        return caseList;
    }

    public boolean updateCase(Cases caseObj) throws SQLException {
        ensureConnection(); 
        String query = "UPDATE Cases SET case_number = ?, case_type = ?, status = ?, filing_date = ?, closing_date = ?, assigned_lawyer = ? WHERE case_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, caseObj.getCaseNumber());
            stmt.setString(2, caseObj.getCaseType());
            stmt.setString(3, caseObj.getStatus());
            stmt.setDate(4, new java.sql.Date(caseObj.getFilingDate().getTime()));
            stmt.setDate(5, caseObj.getClosingDate() != null ? new java.sql.Date(caseObj.getClosingDate().getTime()) : null);
            stmt.setInt(6, caseObj.getAssignedLawyer());
            stmt.setInt(7, caseObj.getCaseId());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deleteCase(int caseId) throws SQLException {
        ensureConnection(); 
        String query = "DELETE FROM Cases WHERE case_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, caseId);
            return stmt.executeUpdate() > 0;
        }
    }
}
