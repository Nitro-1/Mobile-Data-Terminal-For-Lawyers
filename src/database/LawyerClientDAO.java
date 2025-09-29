package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.lawyer_client;

public class LawyerClientDAO {
    private Connection connection;

    public LawyerClientDAO(Connection connection) {
        this.connection = connection != null ? connection : DatabaseManager.getConnection();
    }

    public LawyerClientDAO() {
        this.connection = DatabaseManager.getConnection();
    }

    
    private void ensureConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DatabaseManager.getConnection();
        }
    }

    
    public boolean insertLawyerClient(lawyer_client lc) throws SQLException {
        ensureConnection();
        String query = "INSERT INTO lawyer_client (lawyerId, clientId, caseId) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, lc.getLawyerId());
            stmt.setInt(2, lc.getClientId());
            stmt.setInt(3, lc.getCaseId());
            return stmt.executeUpdate() > 0;
        }
    }

    
    public lawyer_client getLawyerClientById(int id) throws SQLException {
        ensureConnection();
        String query = "SELECT * FROM lawyer_client WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new lawyer_client(
                        rs.getInt("id"),
                        rs.getInt("lawyerId"),
                        rs.getInt("clientId"),
                        rs.getInt("caseId")
                );
            }
        }
        return null;
    }

    
    public List<lawyer_client> getAllLawyerClients() throws SQLException {
        ensureConnection();
        List<lawyer_client> list = new ArrayList<>();
        String query = "SELECT * FROM lawyer_client";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(new lawyer_client(
                        rs.getInt("id"),
                        rs.getInt("lawyerId"),
                        rs.getInt("clientId"),
                        rs.getInt("caseId")
                ));
            }
        }
        return list;
    }

    
    public boolean updateLawyerClient(lawyer_client lc) throws SQLException {
        ensureConnection();
        String query = "UPDATE lawyer_client SET lawyerId = ?, clientId = ?, caseId = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, lc.getLawyerId());
            stmt.setInt(2, lc.getClientId());
            stmt.setInt(3, lc.getCaseId());
            stmt.setInt(4, lc.getId());
            return stmt.executeUpdate() > 0;
        }
    }

    
    public boolean deleteLawyerClient(int id) throws SQLException {
        ensureConnection();
        String query = "DELETE FROM lawyer_client WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
}