package model;

import java.time.LocalDate;

public class Lien {

    private int lienId;
    private int customerId;
    private int loanId;

    private String lienReason;
    private LocalDate lienDate;
    private String status;

    public Lien() {
    }

    public Lien(
            int lienId,
            int customerId,
            int loanId,
            String lienReason,
            LocalDate lienDate,
            String status) {

        this.lienId = lienId;
        this.customerId = customerId;
        this.loanId = loanId;
        this.lienReason = lienReason;
        this.lienDate = lienDate;
        this.status = status;
    }

    public int getLienId() {
        return lienId;
    }

    public void setLienId(int lienId) {
        this.lienId = lienId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public String getLienReason() {
        return lienReason;
    }

    public void setLienReason(String lienReason) {
        this.lienReason = lienReason;
    }

    public LocalDate getLienDate() {
        return lienDate;
    }

    public void setLienDate(LocalDate lienDate) {
        this.lienDate = lienDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}