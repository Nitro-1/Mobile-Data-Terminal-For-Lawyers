package model;

import java.util.Objects;

public class lawyer_client {
    private int id;
    private int lawyerId;
    private int clientId;
    private int caseId;

    
    public lawyer_client() {
    }

    public lawyer_client(int id, int lawyerId, int clientId, int caseId) {
        this.id = id;
        this.lawyerId = lawyerId;
        this.clientId = clientId;
        this.caseId = caseId;
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLawyerId() {
        return lawyerId;
    }

    public void setLawyerId(int lawyerId) {
        this.lawyerId = lawyerId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getCaseId() {
        return caseId;
    }

    public void setCaseId(int caseId) {
        this.caseId = caseId;
    }

    
    @Override
    public String toString() {
        return "LawyerClient{" +
                "id=" + id +
                ", lawyerId=" + lawyerId +
                ", clientId=" + clientId +
                ", caseId=" + caseId +
                '}';
    }

    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        lawyer_client that = (lawyer_client) o;
        return id == that.id &&
                lawyerId == that.lawyerId &&
                clientId == that.clientId &&
                caseId == that.caseId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lawyerId, clientId, caseId);
    }
}
