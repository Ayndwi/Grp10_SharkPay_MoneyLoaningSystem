package manager;

import dao.*;

import model.*;

public class CreditManager {

    private CreditHistoryDAO creditHistoryDAO;
    private CustomerDAO customerDAO;
    private LoanDAO loanDAO;

    public CreditManager() {

        creditHistoryDAO =
                new CreditHistoryDAO();

        customerDAO =
                new CustomerDAO();

        loanDAO =
                new LoanDAO();
    }

    // ==========================================
    // CREDIT HISTORY
    // ==========================================

    public void recordPayment(
            int customerId,
            int loanId,
            String status) {

        int latePayments = 0;

        if (status.equalsIgnoreCase("LATE")
                || status.equalsIgnoreCase("OVERDUE")) {

            latePayments = 1;
        }

        CreditHistory history =
                new CreditHistory(
                        0,
                        customerId,
                        loanId,
                        status,
                        0,
                        latePayments
                );

        creditHistoryDAO.addHistory(history);
    }


    // ==========================================
    // CALCULATE CREDIT LIMIT
    // ==========================================

    public double calculateCreditLimit(
            double monthlySalary,
            int paidLoans) {

        double percentage;

        if (paidLoans == 0) {

            percentage = 0.50;

        }
        else if (paidLoans == 1) {

            percentage = 0.60;

        }
        else if (paidLoans == 2) {

            percentage = 0.70;

        }
        else if (paidLoans == 3) {

            percentage = 0.80;

        }
        else {

            percentage = 1.00;
        }

        return monthlySalary * percentage;
    }


    // ==========================================
    // UPDATE CUSTOMER CREDIT LIMIT
    // ==========================================

    public boolean updateCreditLimit(
            int customerId) {

        var customer =
                customerDAO.getCustomerById(
                        customerId
                );

        if (customer == null) {
            return false;
        }


        int paidLoans =
                loanDAO.countPaidLoans(
                        customerId
                );


        double newCreditLimit =
                calculateCreditLimit(
                        customer.getSalary(),
                        paidLoans
                );


        return customerDAO.updateCreditLimit(
                customerId,
                newCreditLimit
        );
    }
}