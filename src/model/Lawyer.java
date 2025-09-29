package model;

import java.util.Objects;

public class Lawyer {
    private int lawyerId;
    private String firstName;
    private String lastName;
    private String barNumber;
    private String email;
    private String phone;
    private String specialization;
    private int yearsOfExperience;
    private String status;

    public Lawyer() {
    }

    public Lawyer(int lawyerId, String firstName, String lastName, String barNumber, String email, String phone, String specialization, int yearsOfExperience, String status) {
        this.lawyerId = lawyerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.barNumber = barNumber;
        this.email = email;
        this.phone = phone;
        this.specialization = specialization;
        this.yearsOfExperience = yearsOfExperience;
        this.status = status;
    }

    
    public int getLawyerId() {
        return lawyerId;
    }

    public void setLawyerId(int lawyerId) {
        this.lawyerId = lawyerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBarNumber() {
        return barNumber;
    }

    public void setBarNumber(String barNumber) {
        this.barNumber = barNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    
    @Override
    public String toString() {
        return "Lawyer{" +
                "lawyerId=" + lawyerId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", barNumber='" + barNumber + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", specialization='" + specialization + '\'' +
                ", yearsOfExperience=" + yearsOfExperience +
                ", status='" + status + '\'' +
                '}';
    }

   
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lawyer lawyer = (Lawyer) o;
        return lawyerId == lawyer.lawyerId &&
                yearsOfExperience == lawyer.yearsOfExperience &&
                Objects.equals(firstName, lawyer.firstName) &&
                Objects.equals(lastName, lawyer.lastName) &&
                Objects.equals(barNumber, lawyer.barNumber) &&
                Objects.equals(email, lawyer.email) &&
                Objects.equals(phone, lawyer.phone) &&
                Objects.equals(specialization, lawyer.specialization) &&
                Objects.equals(status, lawyer.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lawyerId, firstName, lastName, barNumber, email, phone, specialization, yearsOfExperience, status);
    }
}
