package database;

import model.Warrants;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WarrantsDAO {
    private Connection connection;

    public WarrantsDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean insertWarrant(Warrants warrant) throws SQLException {
        String query = "INSERT INTO Warrants (case_id, warrant_type, issued_date, executed_date, issuing_judge, status, details) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, warrant.getCaseId());
            statement.setString(2, warrant.getWarrantType());
            statement.setDate(3, new java.sql.Date(warrant.getIssuedDate().getTime()));
            statement.setDate(4, warrant.getExecutedDate() != null ? new java.sql.Date(warrant.getExecutedDate().getTime()) : null);
            statement.setString(5, warrant.getIssuingJudge());
            statement.setString(6, warrant.getStatus());
            statement.setString(7, warrant.getDetails());
            return statement.executeUpdate() > 0;
        }
    }

    public Warrants getWarrantById(int warrantId) throws SQLException {
        String query = "SELECT * FROM Warrants WHERE warrant_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, warrantId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Warrants(
                        resultSet.getInt("warrant_id"),
                        resultSet.getInt("case_id"),
                        resultSet.getString("warrant_type"),
                        resultSet.getDate("issued_date"),
                        resultSet.getDate("executed_date"),
                        resultSet.getString("issuing_judge"),
                        resultSet.getString("status"),
                        resultSet.getString("details")
                );
            }
        }
        return null;
    }

    public List<Warrants> getAllWarrants() throws SQLException {
        List<Warrants> warrantList = new ArrayList<>();
        String query = "SELECT * FROM Warrants";
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                warrantList.add(new Warrants(
                        resultSet.getInt("warrant_id"),
                        resultSet.getInt("case_id"),
                        resultSet.getString("warrant_type"),
                        resultSet.getDate("issued_date"),
                        resultSet.getDate("executed_date"),
                        resultSet.getString("issuing_judge"),
                        resultSet.getString("status"),
                        resultSet.getString("details")
                ));
            }
        }
        return warrantList;
    }

    public boolean updateWarrant(Warrants warrant) throws SQLException {
        String query = "UPDATE Warrants SET case_id=?, warrant_type=?, issued_date=?, executed_date=?, issuing_judge=?, status=?, details=? WHERE warrant_id=?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, warrant.getCaseId());
            statement.setString(2, warrant.getWarrantType());
            statement.setDate(3, new java.sql.Date(warrant.getIssuedDate().getTime()));
            statement.setDate(4, warrant.getExecutedDate() != null ? new java.sql.Date(warrant.getExecutedDate().getTime()) : null);
            statement.setString(5, warrant.getIssuingJudge());
            statement.setString(6, warrant.getStatus());
            statement.setString(7, warrant.getDetails());
            statement.setInt(8, warrant.getWarrantId());
            return statement.executeUpdate() > 0;
        }
    }

    public boolean deleteWarrant(int warrantId) throws SQLException {
        String query = "DELETE FROM Warrants WHERE warrant_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, warrantId);
            return statement.executeUpdate() > 0;
        }
    }
}