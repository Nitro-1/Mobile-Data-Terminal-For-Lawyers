package model;

import java.util.Date;
import java.util.Objects;

public class Warrants {
    private int warrantId;
    private int caseId;
    private String warrantType;
    private Date issuedDate;
    private Date executedDate;
    private String issuingJudge;
    private String status;
    private String details;


    public Warrants() {
    }

    public Warrants(int warrantId, int caseId, String warrantType, Date issuedDate, Date executedDate, String issuingJudge, String status, String details) {
        this.warrantId = warrantId;
        this.caseId = caseId;
        this.warrantType = warrantType;
        this.issuedDate = issuedDate;
        this.executedDate = executedDate;
        this.issuingJudge = issuingJudge;
        this.status = status;
        this.details = details;
    }

   
    public int getWarrantId() {
        return warrantId;
    }

    public void setWarrantId(int warrantId) {
        this.warrantId = warrantId;
    }

    public int getCaseId() {
        return caseId;
    }

    public void setCaseId(int caseId) {
        this.caseId = caseId;
    }

    public String getWarrantType() {
        return warrantType;
    }

    public void setWarrantType(String warrantType) {
        this.warrantType = warrantType;
    }

    public Date getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(Date issuedDate) {
        this.issuedDate = issuedDate;
    }

    public Date getExecutedDate() {
        return executedDate;
    }

    public void setExecutedDate(Date executedDate) {
        this.executedDate = executedDate;
    }

    public String getIssuingJudge() {
        return issuingJudge;
    }

    public void setIssuingJudge(String issuingJudge) {
        this.issuingJudge = issuingJudge;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    
    @Override
    public String toString() {
        return "warrents{" +
                "warrantId=" + warrantId +
                ", caseId=" + caseId +
                ", warrantType='" + warrantType + '\'' +
                ", issuedDate=" + issuedDate +
                ", executedDate=" + executedDate +
                ", issuingJudge='" + issuingJudge + '\'' +
                ", status='" + status + '\'' +
                ", details='" + details + '\'' +
                '}';
    }

   
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Warrants that = (Warrants) o;
        return warrantId == that.warrantId &&
                caseId == that.caseId &&
                Objects.equals(warrantType, that.warrantType) &&
                Objects.equals(issuedDate, that.issuedDate) &&
                Objects.equals(executedDate, that.executedDate) &&
                Objects.equals(issuingJudge, that.issuingJudge) &&
                Objects.equals(status, that.status) &&
                Objects.equals(details, that.details);
    }

    @Override
    public int hashCode() {
        return Objects.hash(warrantId, caseId, warrantType, issuedDate, executedDate, issuingJudge, status, details);
    }
}
