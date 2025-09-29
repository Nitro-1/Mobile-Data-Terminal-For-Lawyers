package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.CourtHearings;

public class CourtHearingsDAO {
    private Connection connection;

    public CourtHearingsDAO(Connection connection) {
        this.connection = connection != null ? connection : DatabaseManager.getConnection();
    }

    public CourtHearingsDAO() {
        this.connection = DatabaseManager.getConnection();
    }

    private void ensureConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DatabaseManager.getConnection();
        }
    }

    
    public boolean addCourtHearing(CourtHearings hearing) throws SQLException {
        ensureConnection();
        String query = "INSERT INTO Court_Hearings (case_id, hearing_date, court_name, judge_name, hearing_status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, hearing.getCaseId());
            statement.setDate(2, new java.sql.Date(hearing.getHearingDate().getTime()));
            statement.setString(3, hearing.getCourtName());
            statement.setString(4, hearing.getJudgeName());
            statement.setString(5, hearing.getHearingStatus());

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        hearing.setHearingId(generatedKeys.getInt(1));
                        return true; 
                    }
                }
            }
        }
        return false; 
    }

    public CourtHearings getCourtHearingById(int hearingId) throws SQLException {
        ensureConnection();
        String query = "SELECT * FROM Court_Hearings WHERE hearing_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, hearingId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new CourtHearings(
                    resultSet.getInt("hearing_id"),
                    resultSet.getInt("case_id"),
                    resultSet.getDate("hearing_date"),
                    resultSet.getString("court_name"),
                    resultSet.getString("judge_name"),
                    resultSet.getString("hearing_status")
                );
            }
        }
        return null;
    }

    public List<CourtHearings> getAllCourtHearings() throws SQLException {
        ensureConnection();
        List<CourtHearings> hearings = new ArrayList<>();
        String query = "SELECT * FROM Court_Hearings";
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                hearings.add(new CourtHearings(
                    resultSet.getInt("hearing_id"),
                    resultSet.getInt("case_id"),
                    resultSet.getDate("hearing_date"),
                    resultSet.getString("court_name"),
                    resultSet.getString("judge_name"),
                    resultSet.getString("hearing_status")
                ));
            }
        }
        return hearings;
    }

    
    public boolean deleteCourtHearing(int hearingId) throws SQLException {
        ensureConnection();
        String query = "DELETE FROM Court_Hearings WHERE hearing_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, hearingId);
            return statement.executeUpdate() > 0; 
        }
    }
}
