package database;

import model.Client; 
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO extends BaseDAO {

    public ClientDAO(Connection connection) {
        super(connection); 
    }

    
    public boolean addClient(Client client) throws SQLException {
        ensureConnection(); 
        String query = "INSERT INTO Clients (first_name, last_name, email, phone, address, date_of_birth) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, client.getFirstName());
            statement.setString(2, client.getLastName());
            statement.setString(3, client.getEmail());
            statement.setString(4, client.getPhone());
            statement.setString(5, client.getAddress());
            statement.setDate(6, new java.sql.Date(client.getDateOfBirth().getTime()));
            return statement.executeUpdate() > 0;
        }
    }

    
    public Client getClientById(int clientId) throws SQLException {
        ensureConnection(); 
        String query = "SELECT * FROM Clients WHERE client_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, clientId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Client(
                        resultSet.getInt("client_id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("email"),
                        resultSet.getString("phone"),
                        resultSet.getString("address"),
                        resultSet.getDate("date_of_birth")
                    );
                }
            }
        }
        return null; 
    }

    
    public List<Client> getAllClients() throws SQLException {
        ensureConnection(); 
        List<Client> clients = new ArrayList<>();
        String query = "SELECT * FROM Clients";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                clients.add(new Client(
                    resultSet.getInt("client_id"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getString("email"),
                    resultSet.getString("phone"),
                    resultSet.getString("address"),
                    resultSet.getDate("date_of_birth")
                ));
            }
        }
        return clients;
    }

    
    public boolean updateClient(Client client) throws SQLException {
        ensureConnection(); 
        String query = "UPDATE Clients SET first_name = ?, last_name = ?, email = ?, phone = ?, address = ?, date_of_birth = ? WHERE client_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, client.getFirstName());
            statement.setString(2, client.getLastName());
            statement.setString(3, client.getEmail());
            statement.setString(4, client.getPhone());
            statement.setString(5, client.getAddress());
            statement.setDate(6, new java.sql.Date(client.getDateOfBirth().getTime()));
            statement.setInt(7, client.getClientId());
            return statement.executeUpdate() > 0;
        }
    }

    
    public boolean deleteClient(int clientId) throws SQLException {
        ensureConnection(); 
        String query = "DELETE FROM Clients WHERE client_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, clientId);
            return statement.executeUpdate() > 0;
        }
    }
}
