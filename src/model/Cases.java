package model;

import java.util.Date;
import java.util.Objects;

public class Cases {
    private int caseId;
    private String caseNumber;
    private String caseType;
    private String status;
    private Date filingDate;
    private Date closingDate;
    private int assignedLawyer;

    public Cases() {
    }

    public Cases(int caseId, String caseNumber, String caseType, String status, Date filingDate, Date closingDate, int assignedLawyer) {
        this.caseId = caseId;
        this.caseNumber = caseNumber;
        this.caseType = caseType;
        this.status = status;
        this.filingDate = filingDate;
        this.closingDate = closingDate;
        this.assignedLawyer = assignedLawyer;
    }

    public int getCaseId() {
        return caseId;
    }

    public void setCaseId(int caseId) {
        this.caseId = caseId;
    }

    public String getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getFilingDate() {
        return filingDate;
    }

    public void setFilingDate(Date filingDate) {
        this.filingDate = filingDate;
    }

    public Date getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(Date closingDate) {
        this.closingDate = closingDate;
    }

    public int getAssignedLawyer() {
        return assignedLawyer;
    }

    public void setAssignedLawyer(int assignedLawyer) {
        this.assignedLawyer = assignedLawyer;
    }

    @Override
    public String toString() {
        return "Case{" +
                "caseId=" + caseId +
                ", caseNumber='" + caseNumber + '\'' +
                ", caseType='" + caseType + '\'' +
                ", status='" + status + '\'' +
                ", filingDate=" + filingDate +
                ", closingDate=" + closingDate +
                ", assignedLawyer=" + assignedLawyer +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cases aCase = (Cases) o;
        return caseId == aCase.caseId &&
                assignedLawyer == aCase.assignedLawyer &&
                Objects.equals(caseNumber, aCase.caseNumber) &&
                Objects.equals(caseType, aCase.caseType) &&
                Objects.equals(status, aCase.status) &&
                Objects.equals(filingDate, aCase.filingDate) &&
                Objects.equals(closingDate, aCase.closingDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(caseId, caseNumber, caseType, status, filingDate, closingDate, assignedLawyer);
    }
}
