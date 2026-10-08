package manager;

import dao.*;

import model.Customer;
import model.Loan;

import java.time.LocalDate;

public class LoanManager {

    private LoanDAO loanDAO;
    private LienDAO lienDAO;

    public LoanManager() {

        loanDAO = new LoanDAO();
        lienDAO = new LienDAO();

    }

    public boolean isEligible(Customer customer, double amount) {
    	
    	// Customer cannot apply for a loan while an active lien exists
    	if (lienDAO.hasActiveLien(customer.getCustomerId())) {
    	    return false;
    	}

        // Cannot apply if customer already has an unpaid loan
        Loan currentLoan =
                loanDAO.getCurrentLoan(
                        customer.getCustomerId()
                );

        if (currentLoan != null
                && currentLoan.getRemainingBalance() > 0) {

            return false;
        }

        // Amount must be greater than 0
        if (amount <= 0) {

            return false;

        }

        // Cannot exceed credit limit
        if (amount > customer.getCreditLimit()) {

            return false;

        }

        // Loan cannot exceed twice the salary
        if (amount > customer.getSalary() * 2) {

            return false;

        }

        return true;

    }

    public Loan createLoan(

            Customer customer,

            double amount,

            double interestRate,

            int termMonths) {

        // Check loan eligibility
        if (!isEligible(customer, amount)) {

            return null;

        }

        // Term must be at least 1 month
        if (termMonths <= 0) {

            return null;

        }

        // Calculate interest
        double interest =
                amount * (interestRate / 100);

        // Calculate total amount to repay
        double totalAmount =
                amount + interest;

        // Date the loan was created
        // Uses TestDate so we can simulate future dates
        LocalDate applicationDate =
                TestDate.today();

        // Final due date
        LocalDate dueDate =
                applicationDate.plusMonths(
                        termMonths
                );

        // Create loan object
        Loan loan = new Loan(

                0,

                customer.getCustomerId(),

                amount,

                interestRate,

                termMonths,

                totalAmount,

                totalAmount,

                applicationDate,

                dueDate,

                "APPROVED"

        );

        // Save loan to database
        if (loanDAO.createLoan(loan)) {

            return loan;

        }

        return null;

    }

    public Loan getCurrentLoan(Customer customer) {

        Loan loan =
                loanDAO.getCurrentLoan(
                        customer.getCustomerId()
                );

        if (loan == null) {

            return null;

        }

        // Check if the final due date has passed
        // while there is still a balance.
        //
        // TestDate.today() allows us to pretend
        // that today's date is in the future.
        if (
            TestDate.today().isAfter(
                    loan.getDueDate()
            )
            && loan.getRemainingBalance() > 0
        ) {

            loanDAO.updateBalance(

                    loan.getLoanId(),

                    loan.getRemainingBalance(),

                    "OVERDUE"

            );

            loan.setStatus("OVERDUE");

        }

        return loan;

    }

}
