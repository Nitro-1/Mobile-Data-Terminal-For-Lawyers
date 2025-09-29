package database;

import model.Lawyer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LawyerDAO extends BaseDAO {

    public LawyerDAO(Connection connection) {
        super(connection); 
    }

    
    public boolean authenticateLawyer(String email, String barNumber) throws SQLException {
    if (connection == null) return false; 
    String query = "SELECT * FROM Lawyers WHERE email = ? AND bar_number = ?";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        stmt.setString(1, email);
        stmt.setString(2, barNumber);
        try (ResultSet rs = stmt.executeQuery()) {
            return rs.next();
        }
    }
    
}


    
    public boolean addLawyer(Lawyer lawyer) throws SQLException {
        if (connection == null) return false;
        String query = "INSERT INTO Lawyers (first_name, last_name, bar_number, email, phone, specialization, years_of_experience, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, lawyer.getFirstName());
            stmt.setString(2, lawyer.getLastName());
            stmt.setString(3, lawyer.getBarNumber());
            stmt.setString(4, lawyer.getEmail());
            stmt.setString(5, lawyer.getPhone());
            stmt.setString(6, lawyer.getSpecialization());
            stmt.setInt(7, lawyer.getYearsOfExperience());
            stmt.setString(8, lawyer.getStatus());
            return stmt.executeUpdate() > 0;
        }
    }

    
    public List<Lawyer> getAllLawyers() throws SQLException {
        if (connection == null) return new ArrayList<>();
        List<Lawyer> lawyers = new ArrayList<>();
        String query = "SELECT * FROM Lawyers";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                lawyers.add(new Lawyer(
                    rs.getInt("lawyer_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("bar_number"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("specialization"),
                    rs.getInt("years_of_experience"),
                    rs.getString("status")
                ));
            }
        }
        return lawyers;
    }

    
    public Lawyer getLawyerById(int id) throws SQLException {
        if (connection == null) return null;
        String query = "SELECT * FROM Lawyers WHERE lawyer_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Lawyer(
                        rs.getInt("lawyer_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("bar_number"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("specialization"),
                        rs.getInt("years_of_experience"),
                        rs.getString("status")
                    );
                }
            }
        }
        return null;
    }

    
    public boolean updateLawyer(Lawyer lawyer) throws SQLException {
        if (connection == null) return false;
        String query = "UPDATE Lawyers SET first_name = ?, last_name = ?, bar_number = ?, email = ?, phone = ?, specialization = ?, years_of_experience = ?, status = ? WHERE lawyer_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, lawyer.getFirstName());
            stmt.setString(2, lawyer.getLastName());
            stmt.setString(3, lawyer.getBarNumber());
            stmt.setString(4, lawyer.getEmail());
            stmt.setString(5, lawyer.getPhone());
            stmt.setString(6, lawyer.getSpecialization());
            stmt.setInt(7, lawyer.getYearsOfExperience());
            stmt.setString(8, lawyer.getStatus());
            stmt.setInt(9, lawyer.getLawyerId());
            return stmt.executeUpdate() > 0;
        }
    }

    
    public boolean deleteLawyer(int id) throws SQLException {
        if (connection == null) return false;
        String query = "DELETE FROM Lawyers WHERE lawyer_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
}
