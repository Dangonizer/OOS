import bank.Payment;
import bank.PrivateBank;
import bank.exceptions.AccountAlreadyExistsException;
import bank.exceptions.AccountDoesNotExistException;
import bank.exceptions.TransactionAlreadyExistException;
import bank.exceptions.TransactionAttributeException;
import ui.FxApplication;

import java.io.IOException;

/**
 * Main class for testing functionality
 *
 * @author Kevin Schier
 * @version 1.1
 */
public class Main {
    public static void main(String[] args) {
        PrivateBank bank1;
        try {
            bank1 = new PrivateBank("Testbank", 0.1, 0.2, "src/main/resources/data/Testbank");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            bank1.createAccount("Testaccount1");
        } catch (AccountAlreadyExistsException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            bank1.addTransaction("Testaccount1", new Payment("sdsfsdf", 2314234D, "fdgrete"));
        } catch (TransactionAlreadyExistException e) {
            throw new RuntimeException(e);
        } catch (AccountDoesNotExistException e) {
            throw new RuntimeException(e);
        } catch (TransactionAttributeException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
