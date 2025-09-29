package model;

import java.util.Objects;

public class users {
    private int userId;
    private String username;
    private String passwordHash;
    private String role;
    private Integer lawyerId;

    
    public users() {
    }

    public users(int userId, String username, String passwordHash, String role, Integer lawyerId) {
        this.userId = userId;
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
        this.lawyerId = lawyerId;
    }

    
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getLawyerId() {
        return lawyerId;
    }

    public void setLawyerId(Integer lawyerId) {
        this.lawyerId = lawyerId;
    }

    // toString Method
    @Override
    public String toString() {
        return "users{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", role='" + role + '\'' +
                ", lawyerId=" + lawyerId +
                '}';
    }

    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        users that = (users) o;
        return userId == that.userId &&
                Objects.equals(username, that.username) &&
                Objects.equals(passwordHash, that.passwordHash) &&
                Objects.equals(role, that.role) &&
                Objects.equals(lawyerId, that.lawyerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, username, passwordHash, role, lawyerId);
    }
}