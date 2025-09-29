package database;

import model.ActivityLogs; 
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActivityLogsDAO extends BaseDAO {

    public ActivityLogsDAO(Connection connection) {
        super(connection);
    }

    // Create a new activity log entry
    public void addActivityLog(ActivityLogs log) throws SQLException {
        ensureConnection(); 
        String query = "INSERT INTO Activity_Logs (user_id, action, timestamp) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, log.getUserId());
            stmt.setString(2, log.getAction());
            stmt.setString(3, log.getTimestamp());
            stmt.executeUpdate();
        }
    }

    
    public ActivityLogs getActivityLogById(int logId) throws SQLException {
        ensureConnection(); // Ensure the connection is valid
        String query = "SELECT * FROM Activity_Logs WHERE log_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, logId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new ActivityLogs(
                    rs.getInt("log_id"),
                    rs.getInt("user_id"),
                    rs.getString("action"),
                    rs.getString("timestamp")
                );
            }
        }
        return null;
    }

    
    public List<ActivityLogs> getAllActivityLogs() throws SQLException {
        ensureConnection(); 
        List<ActivityLogs> logs = new ArrayList<>();
        String query = "SELECT * FROM Activity_Logs";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                logs.add(new ActivityLogs(
                    rs.getInt("log_id"),
                    rs.getInt("user_id"),
                    rs.getString("action"),
                    rs.getString("timestamp")
                ));
            }
        }
        return logs;
    }

    
    public void deleteActivityLog(int logId) throws SQLException {
        ensureConnection(); 
        String query = "DELETE FROM Activity_Logs WHERE log_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, logId);
            stmt.executeUpdate();
        }
    }
}