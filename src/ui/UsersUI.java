package ui;

import database.UsersDAO;
import model.users;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class UsersUI {
    private UsersDAO usersDAO;
    private Scanner scanner;

    public UsersUI(Connection connection) {
        this.usersDAO = new UsersDAO(connection);
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        while (true) {
            System.out.println("\nUser Management System");
            System.out.println("1. Add User");
            System.out.println("2. View User by ID");
            System.out.println("3. View All Users");
            System.out.println("4. Update User");
            System.out.println("5. Delete User");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1: addUser(); break;
                case 2: viewUserById(); break;
                case 3: viewAllUsers(); break;
                case 4: updateUser(); break;
                case 5: deleteUser(); break;
                case 6: System.out.println("Exiting..."); return;
                default: System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void addUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter role: ");
        String role = scanner.nextLine();
        System.out.print("Enter lawyer ID (or press Enter for none): ");
        String lawyerIdInput = scanner.nextLine();
        Integer lawyerId = lawyerIdInput.isEmpty() ? null : Integer.parseInt(lawyerIdInput);
        
        users user = new users(0, username, password, role, lawyerId);
        if (usersDAO.addUser(user)) {
            System.out.println("User added successfully!");
        } else {
            System.out.println("Failed to add user.");
        }
    }

    private void viewUserById() {
        System.out.print("Enter User ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine();
        users user = usersDAO.getUserById(userId);
        if (user != null) {
            System.out.println("User Found: " + user);
        } else {
            System.out.println("User not found.");
        }
    }

    private void viewAllUsers() {
        List<users> userList = usersDAO.getAllUsers();
        if (userList.isEmpty()) {
            System.out.println("No users found.");
        } else {
            for (users user : userList) {
                System.out.println(user);
            }
        }
    }

    private void updateUser() {
        System.out.print("Enter User ID to update: ");
        int userId = scanner.nextInt();
        scanner.nextLine();
        users existingUser = usersDAO.getUserById(userId);
        if (existingUser == null) {
            System.out.println("User not found.");
            return;
        }
        
        System.out.print("Enter new username (or press Enter to keep current): ");
        String username = scanner.nextLine();
        if (username.isEmpty()) username = existingUser.getUsername();
        
        System.out.print("Enter new password (or press Enter to keep current): ");
        String password = scanner.nextLine();
        if (password.isEmpty()) password = existingUser.getPasswordHash();
        
        System.out.print("Enter new role (or press Enter to keep current): ");
        String role = scanner.nextLine();
        if (role.isEmpty()) role = existingUser.getRole();
        
        System.out.print("Enter new lawyer ID (or press Enter to keep current): ");
        String lawyerIdInput = scanner.nextLine();
        Integer lawyerId = lawyerIdInput.isEmpty() ? existingUser.getLawyerId() : Integer.parseInt(lawyerIdInput);
        
        users updatedUser = new users(userId, username, password, role, lawyerId);
        if (usersDAO.updateUser(updatedUser)) {
            System.out.println("User updated successfully!");
        } else {
            System.out.println("Failed to update user.");
        }
    }

    private void deleteUser() {
        System.out.print("Enter User ID to delete: ");
        int userId = scanner.nextInt();
        scanner.nextLine();
        if (usersDAO.deleteUser(userId)) {
            System.out.println("User deleted successfully!");
        } else {
            System.out.println("Failed to delete user.");
        }
    }

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_database", "your_username", "your_password")) {
            UsersUI usersUI = new UsersUI(connection);
            usersUI.showMenu();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
