package model;

public class CreditHistory {

    private int creditHistoryId;
    private int customerId;
    private Integer loanId;

    private String paymentStatus;
    private int missedPayments;
    private int latePayments;

    public CreditHistory() {
    }

    public CreditHistory(int creditHistoryId,
                         int customerId,
                         Integer loanId,
                         String paymentStatus,
                         int missedPayments,
                         int latePayments) {

        this.creditHistoryId = creditHistoryId;
        this.customerId = customerId;
        this.loanId = loanId;
        this.paymentStatus = paymentStatus;
        this.missedPayments = missedPayments;
        this.latePayments = latePayments;
    }

    public int getCreditHistoryId() {
        return creditHistoryId;
    }

    public void setCreditHistoryId(int creditHistoryId) {
        this.creditHistoryId = creditHistoryId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public int getMissedPayments() {
        return missedPayments;
    }

    public void setMissedPayments(int missedPayments) {
        this.missedPayments = missedPayments;
    }

    public int getLatePayments() {
        return latePayments;
    }

    public void setLatePayments(int latePayments) {
        this.latePayments = latePayments;
    }
}