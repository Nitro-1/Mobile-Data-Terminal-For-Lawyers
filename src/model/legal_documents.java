package model;

import java.util.Date;
import java.util.Objects;

public class legal_documents {
    private int documentId;
    private int caseId;
    private String documentName;
    private String documentType;
    private Date uploadDate;
    private String filePath;

    
    public legal_documents() {
    }

    public legal_documents(int documentId, int caseId, String documentName, String documentType, Date uploadDate, String filePath) {
        this.documentId = documentId;
        this.caseId = caseId;
        this.documentName = documentName;
        this.documentType = documentType;
        this.uploadDate = uploadDate;
        this.filePath = filePath;
    }

    
    public int getDocumentId() {
        return documentId;
    }

    public void setDocumentId(int documentId) {
        this.documentId = documentId;
    }

    public int getCaseId() {
        return caseId;
    }

    public void setCaseId(int caseId) {
        this.caseId = caseId;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    
    @Override
    public String toString() {
        return "LegalDocuments{" +
                "documentId=" + documentId +
                ", caseId=" + caseId +
                ", documentName='" + documentName + '\'' +
                ", documentType='" + documentType + '\'' +
                ", uploadDate=" + uploadDate +
                ", filePath='" + filePath + '\'' +
                '}';
    }

    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        legal_documents that = (legal_documents) o;
        return documentId == that.documentId &&
                caseId == that.caseId &&
                Objects.equals(documentName, that.documentName) &&
                Objects.equals(documentType, that.documentType) &&
                Objects.equals(uploadDate, that.uploadDate) &&
                Objects.equals(filePath, that.filePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(documentId, caseId, documentName, documentType, uploadDate, filePath);
    }
}
