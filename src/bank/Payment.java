package bank;

import bank.exceptions.TransactionAttributeException;
import bank.exceptions.TransactionInvalidValueException;

/**
 * This class manages withdrawals and deposits
 *
 * @author Kevin Schier
 * @version 1.1
 */
public class Payment extends Transaction {
    /**
     * Incoming interest rate as decimal number. Allowed values are 0-1
     */
    private double incomingInterest;

    /**
     * Incoming interest rate as decimal number. Allowed values are 0-1
     */
    private double outgoingInterest;

    /**
     * Returns {@link #incomingInterest}
     *
     * @return Value of incomingInterest
     */
    public double getIncomingInterest() {
        return incomingInterest;
    }

    /**
     * Sets incomingInterest if value is in allowed range(0-1), otherwise writes error to console and does not set value.
     *
     * @param incomingInterest Interest rate that should be set (0-1 allowed)
     */
    public void setIncomingInterest(double incomingInterest) {
        try {
            if (incomingInterest < 0 || incomingInterest > 1) {
                throw new TransactionInvalidValueException("Error: No incoming interest rates bellow 0 and above 100% allowed for transfers.");
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return;
        }

        this.incomingInterest = incomingInterest;
    }

    /**
     * Returns outgoing interest rate.
     *
     * @return Outgoing interest rate
     */
    public double getOutgoingInterest() {
        return outgoingInterest;
    }

    /**
     * Sets outgoingInterest if value is in allowed range(0-1), otherwise writes error to console and does not set value.
     *
     * @param outgoingInterest Interest rate that should be set (0-1 allowed)
     */
    public void setOutgoingInterest(double outgoingInterest) {
        try {
            if (outgoingInterest < 0 || outgoingInterest > 1) {
                throw new TransactionInvalidValueException("Error: No outgoing interest rates bellow 0 and above 100% allowed for transfers.");
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return;
        }
        this.outgoingInterest = outgoingInterest;
    }


    /**
     * Constructor with minimal amount of parameters.
     *
     * @param date        Date in "DD.MM:YYYY" format
     * @param amount      Amount of the payment
     * @param description Description of payment
     */
    public Payment(String date, double amount, String description) {
        super(date, amount, description);
    }

    /**
     * Full constructor that sets all attributes
     *
     * @param date             Date in "DD.MM:YYYY" format
     * @param amount           Amount of the payment
     * @param description      Description of payment
     * @param incomingInterest Decimal interest rate for incoming payments
     * @param outgoingInterest Decimal interest rate for outgoing payments
     */
    public Payment(String date, double amount, String description, double incomingInterest, double outgoingInterest) {
        this(date, amount, description);
        this.setIncomingInterest(incomingInterest);
        this.setOutgoingInterest(outgoingInterest);
    }

    /**
     * Copies content of parameter {@link Payment} object to created object
     *
     * @param payment Object that will be copied
     */
    public Payment(Payment payment) {
        this.date = payment.date;
        this.amount = payment.amount;
        this.description = payment.description;
        this.incomingInterest = payment.incomingInterest;
        this.outgoingInterest = payment.outgoingInterest;
    }

    /**
     * Returns all attributes as string
     *
     * @return String of attributes
     */
    @Override
    public String toString() {
        return super.toString() +
                ", incomingInterest=" + incomingInterest +
                ", outgoingInterest=" + outgoingInterest;
    }

    /**
     * Calculates amount after factoring in interest rates
     *
     * @return New amount after interest has been factored in
     */
    @Override
    public double calculate() {
        if (amount >= 0) {
            return amount - amount * incomingInterest;
        } else
            return amount + amount * outgoingInterest;
    }

    /**
     * Checks object and parameter o for equal contents
     *
     * @param o Object that is compared
     * @return Returns true if contents of both objects are the same
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Payment)) {
            return false;
        }
        Payment p = (Payment) o;
        return super.equals((Transaction) o) && incomingInterest == p.incomingInterest && outgoingInterest == p.outgoingInterest;
    }
}
