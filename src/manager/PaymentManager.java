package manager;

import dao.PaymentDAO;
import dao.LoanDAO;

import model.Loan;
import model.Payment;

import java.time.LocalDate;

public class PaymentManager {

    private PaymentDAO paymentDAO;
    private LoanDAO loanDAO;
    private CreditManager creditManager;

    public PaymentManager() {

        paymentDAO = new PaymentDAO();
        loanDAO = new LoanDAO();
        creditManager = new CreditManager();
    }

    public boolean makePayment(
            Loan loan,
            double paymentAmount,
            LocalDate paymentDate,
            String paymentMethod) {

        // ==========================================
        // BASIC VALIDATION
        // ==========================================

        if (loan == null) {
            return false;
        }

        if (paymentAmount <= 0) {
            return false;
        }

        /*
         * Do not allow payment greater than
         * the current remaining balance.
         */
        if (paymentAmount > loan.getRemainingBalance()) {
            return false;
        }
        
        int paymentsMade =
                paymentDAO.countPaymentsByLoanId(loan.getLoanId());


        // ==========================================
        // CALCULATE MONTHLY PAYMENT
        // ==========================================

        double monthlyPayment =
                loan.getTotalAmount()
                / loan.getTermMonths();


        // ==========================================
        // CHECK PAYMENT AMOUNT
        // ==========================================

        /*
         * If this is NOT the final payment,
         * require the customer to pay the
         * calculated monthly installment.
         *
         * Small difference is allowed because
         * of decimal rounding.
         */

        if (loan.getRemainingBalance()
                > monthlyPayment) {

            if (Math.abs(
                    paymentAmount - monthlyPayment
                ) > 0.01) {

                return false;
            }
        }


        // ==========================================
        // GET CURRENT BALANCE
        // ==========================================

        double balance =
                loan.getRemainingBalance();


        // ==========================================
        // CHECK IF PAYMENT IS LATE
        // ==========================================

     // Determine the due date for the current monthly payment
        int paymentNumber = paymentsMade + 1;

        LocalDate monthlyDueDate =
                loan.getApplicationDate()
                     .plusMonths(paymentNumber);

        // Check if the current monthly payment is late
        boolean late =
                paymentDate.isAfter(monthlyDueDate);


        // ==========================================
        // APPLY LATE PENALTY
        // ==========================================

        if (late) {

            double penaltyRate;

            if (loan.getLoanAmount() <= 10000) {

                penaltyRate = 0.05;

            }
            else if (loan.getLoanAmount() <= 50000) {

                penaltyRate = 0.10;

            }
            else {

                penaltyRate = 0.15;
            }

            double penalty =
                    balance * penaltyRate;

            balance += penalty;
        }


        // ==========================================
        // SUBTRACT PAYMENT
        // ==========================================

        balance -= paymentAmount;

        if (balance < 0) {
            balance = 0;
        }


        // ==========================================
        // DETERMINE LOAN STATUS
        // ==========================================

        String status;

        if (balance == 0) {

            status = "PAID";

        }
        else if (late) {

            status = "OVERDUE";

        }
        else {

            status = "APPROVED";
        }


        // ==========================================
        // CREATE PAYMENT RECORD
        // ==========================================

        Payment payment =
                new Payment(
                        0,
                        loan.getLoanId(),
                        paymentAmount,
                        paymentDate,
                        paymentMethod
                );


        // ==========================================
        // SAVE PAYMENT
        // ==========================================

        boolean paymentSaved =
                paymentDAO.addPayment(payment);

        if (!paymentSaved) {
            return false;
        }


        // ==========================================
        // UPDATE LOAN
        // ==========================================

        boolean loanUpdated =
                loanDAO.updateBalance(
                        loan.getLoanId(),
                        balance,
                        status
                );

        if (!loanUpdated) {
            return false;
        }


	     // ==========================================
	     // UPDATE LOAN OBJECT
	     // ==========================================
	
	     loan.setRemainingBalance(balance);
	     loan.setStatus(status);
	
	
	     // ==========================================
	     // RECORD CREDIT HISTORY
	     // ==========================================
	
	     String creditStatus;
	
	     if (late) {
	         creditStatus = "LATE";
	     } else {
	         creditStatus = "ON_TIME";
	     }
	
	     creditManager.recordPayment(
	             loan.getCustomerId(),
	             loan.getLoanId(),
	             creditStatus
	     );
	
	
	     // ==========================================
	     // INCREASE CREDIT LIMIT
	     // ==========================================
	
	     if (status.equals("PAID")) {
	
	         creditManager.updateCreditLimit(
	                 loan.getCustomerId()
	         );
	     }
	
	     return true;
    }
}