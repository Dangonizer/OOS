package bank.exceptions;

public class AccountAlreadyExistsException extends Exception {
    public AccountAlreadyExistsException(String errorMessage) {
        super(errorMessage);
    }
}
