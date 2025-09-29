package model;

import java.util.Date;
import java.util.Objects;

public class CourtHearings {  
    private int hearingId;
    private int caseId;
    private Date hearingDate;
    private String courtName;
    private String judgeName;
    private String hearingStatus;

    public CourtHearings() {
    }

    public CourtHearings(int hearingId, int caseId, Date hearingDate, String courtName, String judgeName, String hearingStatus) {
        this.hearingId = hearingId;
        this.caseId = caseId;
        this.hearingDate = hearingDate;
        this.courtName = courtName;
        this.judgeName = judgeName;
        this.hearingStatus = hearingStatus;
    }

    public int getHearingId() {
        return hearingId;
    }

    public void setHearingId(int hearingId) {
        this.hearingId = hearingId;
    }

    public int getCaseId() {
        return caseId;
    }

    public void setCaseId(int caseId) {
        this.caseId = caseId;
    }

    public Date getHearingDate() {
        return hearingDate;
    }

    public void setHearingDate(Date hearingDate) {
        this.hearingDate = hearingDate;
    }

    public String getCourtName() {
        return courtName;
    }

    public void setCourtName(String courtName) {
        this.courtName = courtName;
    }

    public String getJudgeName() {
        return judgeName;
    }

    public void setJudgeName(String judgeName) {
        this.judgeName = judgeName;
    }

    public String getHearingStatus() {
        return hearingStatus;
    }

    public void setHearingStatus(String hearingStatus) {
        this.hearingStatus = hearingStatus;
    }

    @Override
    public String toString() {
        return "CourtHearings{" +  // ✅ Updated to match new class name
                "hearingId=" + hearingId +
                ", caseId=" + caseId +
                ", hearingDate=" + hearingDate +
                ", courtName='" + courtName + '\'' +
                ", judgeName='" + judgeName + '\'' +
                ", hearingStatus='" + hearingStatus + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourtHearings that = (CourtHearings) o;
        return hearingId == that.hearingId &&
                caseId == that.caseId &&
                Objects.equals(hearingDate, that.hearingDate) &&
                Objects.equals(courtName, that.courtName) &&
                Objects.equals(judgeName, that.judgeName) &&
                Objects.equals(hearingStatus, that.hearingStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hearingId, caseId, hearingDate, courtName, judgeName, hearingStatus);
    }
}
