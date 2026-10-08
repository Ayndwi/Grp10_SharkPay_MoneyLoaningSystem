package model;

import java.time.LocalDate;

public class Loan {

    private int loanId;
    private int customerId;
    private double loanAmount;
    private double interestRate;
    private int termMonths;
    private double totalAmount;
    private double remainingBalance;
    private LocalDate applicationDate;
    private LocalDate dueDate;
    private String status;

    public Loan() {
    }

    public Loan(
            int loanId,
            int customerId,
            double loanAmount,
            double interestRate,
            int termMonths,
            double totalAmount,
            double remainingBalance,
            LocalDate applicationDate,
            LocalDate dueDate,
            String status) {

        this.loanId = loanId;
        this.customerId = customerId;
        this.loanAmount = loanAmount;
        this.interestRate = interestRate;
        this.termMonths = termMonths;
        this.totalAmount = totalAmount;
        this.remainingBalance = remainingBalance;
        this.applicationDate = applicationDate;
        this.dueDate = dueDate;
        this.status = status;
    }

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public int getTermMonths() {
        return termMonths;
    }

    public void setTermMonths(int termMonths) {
        this.termMonths = termMonths;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getRemainingBalance() {
        return remainingBalance;
    }

    public void setRemainingBalance(double remainingBalance) {
        this.remainingBalance = remainingBalance;
    }

    public LocalDate getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(LocalDate applicationDate) {
        this.applicationDate = applicationDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getMonthlyPayment() {

        if (termMonths <= 0) {
            return 0;
        }

        return totalAmount / termMonths;
    }
}