package bank.exceptions;

public class TransactionInvalidValueException extends Exception {
    public TransactionInvalidValueException(String errorMessage) {
        super(errorMessage);
    }
}