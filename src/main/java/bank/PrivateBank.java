package bank;

import bank.exceptions.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Class for a private bank. Implements {@link Bank}.
 *
 * @author Kevin Schier
 * @version 1.0
 */
public class PrivateBank implements Bank {

    //Attributes

    /**
     * Name of the bank.
     */
    String name;

    /**
     * Interest rate for incoming transactions. Allowed values 0-1.
     */
    double incomingInterest;

    /**
     * Interest rate for outgoing transactions. Allowed values 0-1.
     */
    double outgoingInterest;

    /**
     * Maps accounts to list of transactions.
     */
    Map<String, List<Transaction>> accountsToTransactions = new HashMap<>();

    /**
     * Directory name where all transactions pre account per file are stored.
     */
    String directoryName;

    /**
     * Gson instance for class PrivateBank (shared across all instances)
     */
    public static final Gson gson = new GsonBuilder()
            .registerTypeHierarchyAdapter(Transaction.class, new TransactionSerializer())
            .setPrettyPrinting()
            .create();

    //Getters and Setters

    /**
     * Returns name of bank
     *
     * @return Returns {@link #name}
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name of bank
     *
     * @param name Name of bank
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns incoming interest rate
     *
     * @return Returns {@link #incomingInterest}
     */
    public double getIncomingInterest() {
        return incomingInterest;
    }

    /**
     * Sets incoming interest rate
     *
     * @param incomingInterest Incoming interest rate
     */
    public void setIncomingInterest(double incomingInterest) {
        try {
            if (incomingInterest < 0 || incomingInterest > 1) {
                throw new TransactionInvalidValueException("Error: No incoming interest rates bellow 0 and above 100% allowed for transfers.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        this.incomingInterest = incomingInterest;
    }

    /**
     * Returns outgoing interest rate
     *
     * @return Returns {@link #outgoingInterest}
     */
    public double getOutgoingInterest() {
        return outgoingInterest;
    }

    /**
     * Sets outgoing interest rate
     *
     * @param outgoingInterest Outgoing interest rate
     */
    public void setOutgoingInterest(double outgoingInterest) {
        try {
            if (outgoingInterest < 0 || outgoingInterest > 1) {
                throw new TransactionInvalidValueException("Error: No outgoing interest rates bellow 0 and above 100% allowed for transfers.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        this.outgoingInterest = outgoingInterest;
    }

    public String getDirectoryName() {
        return directoryName;
    }

    //Constructors

    /**
     * Instantiates object.
     *
     * @param name             Name of bank
     * @param incomingInterest Incoming interest rate
     * @param outgoingInterest Outgoing interest rate
     */
    public PrivateBank(String name, double incomingInterest, double outgoingInterest, String directoryName) throws IOException {
        this.name = name;
        this.setIncomingInterest(incomingInterest);
        this.setOutgoingInterest(outgoingInterest);
        this.directoryName = directoryName;
        readAccounts();
    }

    /**
     * Copy Constructor
     *
     * @param bank Object to be copied
     */
    public PrivateBank(PrivateBank bank) throws IOException {
        this(bank.name, bank.incomingInterest, bank.outgoingInterest, bank.directoryName);
    }

    //Methods

    @Override
    public String toString() {
        return "PrivateBank{" +
                "name='" + name + '\'' +
                ", incomingInterest=" + incomingInterest +
                ", outgoingInterest=" + outgoingInterest +
                //", accountsToTransactions=" + accountsToTransactions +
                ", directoryName='" + directoryName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrivateBank that = (PrivateBank) o;
        return Double.compare(that.incomingInterest, incomingInterest) == 0 && Double.compare(that.outgoingInterest, outgoingInterest) == 0 && Objects.equals(name, that.name) && Objects.equals(accountsToTransactions, that.accountsToTransactions) && Objects.equals(directoryName, that.directoryName);
    }

    /**
     * Adds an account to the bank.
     *
     * @param account the account to be added
     * @throws AccountAlreadyExistsException if the account already exists
     */
    @Override
    public void createAccount(String account) throws AccountAlreadyExistsException, IOException {
        if (accountsToTransactions.containsKey(account))
            throw new AccountAlreadyExistsException("Error: Account already exists.");
        else {
            List<Transaction> list = new ArrayList<Transaction>();
            accountsToTransactions.put(account, list);
            writeAccount(account);
        }
    }

    /**
     * Adds an account (with specified transactions) to the bank.
     * Important: duplicate transactions must not be added to the account!
     *
     * @param account      the account to be added
     * @param transactions a list of already existing transactions which should be added to the newly created account
     * @throws AccountAlreadyExistsException    if the account already exists
     * @throws TransactionAlreadyExistException if the transaction already exists
     * @throws TransactionAttributeException    if the validation check for certain attributes fail
     */
    @Override
    public void createAccount(String account, List<Transaction> transactions) throws AccountAlreadyExistsException, TransactionAlreadyExistException, TransactionAttributeException, IOException {
        createAccount(account);
        for (Transaction t : transactions) {
            try {
                addTransaction(account, t);
            } catch (AccountDoesNotExistException e) {
                e.printStackTrace();
                return;
            }
        }
        writeAccount(account);
    }

    /**
     * Adds a transaction to an already existing account.
     *
     * @param account     the account to which the transaction is added
     * @param transaction the transaction which should be added to the specified account
     * @throws TransactionAlreadyExistException if the transaction already exists
     * @throws AccountDoesNotExistException     if the specified account does not exist
     * @throws TransactionAttributeException    if the validation check for certain attributes fail
     */
    @Override
    public void addTransaction(String account, Transaction transaction) throws TransactionAlreadyExistException, AccountDoesNotExistException, TransactionAttributeException, IOException {
        System.out.println(accountsToTransactions);
        if (!accountsToTransactions.containsKey(account))
            throw new AccountDoesNotExistException("Error: Account " + account + " does not exist.");
        if (transaction instanceof Payment p) {
            if (p.getIncomingInterest() < 0 || p.getIncomingInterest() > 1 || p.getOutgoingInterest() < 0 || p.getOutgoingInterest() > 1)
                throw new TransactionAttributeException("Error: Invalid value for interest rates.");
            else {
                p.setIncomingInterest(incomingInterest);
                p.setOutgoingInterest(outgoingInterest);
                if (accountsToTransactions.get(account) == null)
                    accountsToTransactions.put(account, new ArrayList<Transaction>());
                if (accountsToTransactions.get(account).contains(transaction))
                    throw new TransactionAlreadyExistException("Error: Transaction already exists.");
                accountsToTransactions.get(account).add(p);
            }
        }
        if (transaction instanceof Transfer t) {
            if (t.getAmount() < 0)
                throw new TransactionAttributeException("Error: Invalid value for interest rates.");
            if (accountsToTransactions.get(account) == null)
                accountsToTransactions.put(account, new ArrayList<Transaction>());
            if (accountsToTransactions.get(account).contains(transaction))
                throw new TransactionAlreadyExistException("Error: Transaction already exists.");
            accountsToTransactions.get(account).add(t);
        }
        writeAccount(account);
    }

    /**
     * Removes a transaction from an account. If the transaction does not exist, an exception is
     * thrown.
     *
     * @param account     the account from which the transaction is removed
     * @param transaction the transaction which is removed from the specified account
     * @throws AccountDoesNotExistException     if the specified account does not exist
     * @throws TransactionDoesNotExistException if the transaction cannot be found
     */
    @Override
    public void removeTransaction(String account, Transaction transaction) throws AccountDoesNotExistException, TransactionDoesNotExistException, IOException {
        if (!accountsToTransactions.containsKey(account))
            throw new AccountDoesNotExistException("Error: Account " + account + " does not exist.");
        else if (!accountsToTransactions.get(account).contains(transaction))
            throw new TransactionDoesNotExistException("Error: Transaction " + transaction + " does not exist.");
        else {
            accountsToTransactions.get(account).remove(transaction);
            writeAccount(account);
        }
    }

    /**
     * Deletes an Account with all transactions
     *
     * @param account the account that is deleted
     * @throws AccountDoesNotExistException if the specified account does not exist
     */
    public void deleteAccount(String account) throws AccountDoesNotExistException, IOException {
        if (!accountsToTransactions.containsKey(account))
            throw new AccountDoesNotExistException("Error: Account " + account + " does not exist.");
        else {
            accountsToTransactions.remove(account);
            File file = new File(directoryName + "/" + account + ".json");
            file.delete();
        }
    }

    public List<String> getAllAccounts() {
        List<String> accounts = new ArrayList<String>();
        for (String account : accountsToTransactions.keySet()) {
            accounts.add(account);
        }
        return accounts;
    }

    /**
     * Checks whether the specified transaction for a given account exists.
     *
     * @param account     the account from which the transaction is checked
     * @param transaction the transaction to search/look for
     */
    @Override
    public boolean containsTransaction(String account, Transaction transaction) {
        if (!accountsToTransactions.containsKey(account))
            return false;
        return accountsToTransactions.get(account).contains(transaction);
    }

    /**
     * Calculates and returns the current account balance.
     *
     * @param account the selected account
     * @return the current account balance
     */
    @Override
    public double getAccountBalance(String account) {
        double balance = 0;
        if (!accountsToTransactions.containsKey(account))
            return balance;
        for (Transaction t : accountsToTransactions.get(account)) {
            balance += t.calculate();
        }
        return balance;
    }

    /**
     * Returns a list of transactions for an account.
     *
     * @param account the selected account
     * @return the list of all transactions for the specified account
     */
    @Override
    public List<Transaction> getTransactions(String account) {
        List<Transaction> empty = new ArrayList<Transaction>();
        if (!accountsToTransactions.containsKey(account))
            return empty;
        else
            return accountsToTransactions.get(account);
    }

    /**
     * Returns a sorted list (-> calculated amounts) of transactions for a specific account. Sorts the list either in ascending or descending order
     * (or empty).
     *
     * @param account the selected account
     * @param asc     selects if the transaction list is sorted in ascending or descending order
     * @return the sorted list of all transactions for the specified account
     */
    @Override
    public List<Transaction> getTransactionsSorted(String account, boolean asc) {
        if (!accountsToTransactions.containsKey(account)) {
            return new ArrayList<Transaction>();
        }
        List<Transaction> trans = new ArrayList<>(accountsToTransactions.get(account));
        if (trans.isEmpty())
            return new ArrayList<Transaction>();
        else {
            trans.sort(Comparator.comparingDouble(Transaction::calculate));
            if (!asc) {
                trans = new ArrayList<>(trans.reversed());
            }
            return trans;
        }
    }

    /**
     * Returns a list of either positive or negative transactions (-> calculated amounts).
     *
     * @param account  the selected account
     * @param positive selects if positive or negative transactions are listed
     * @return the list of all transactions by type
     */
    @Override
    public List<Transaction> getTransactionsByType(String account, boolean positive) {
        if (!accountsToTransactions.containsKey(account)) {
            return new ArrayList<Transaction>();
        }
        List<Transaction> transactions = new ArrayList<>(getTransactions(account));
        if (transactions.isEmpty())
            return transactions;
        if (positive)
            transactions.removeIf(t -> t.calculate() < 0);
        else
            transactions.removeIf(t -> t.calculate() >= 0);
        return transactions;
    }

    void readAccounts() throws IOException {
        File[] files = new File(directoryName).listFiles();
        if (files == null) {
            return;
        } else {
            for (File file : files) {
                String account = file.getName().substring(0, file.getName().length() - 5);
                List<Transaction> transactions = new ArrayList<Transaction>();
                try {
                    String json = Files.readString(file.toPath());
                    transactions = gson.fromJson(json, new TypeToken<List<Transaction>>() {
                    }.getType());
                } catch (Exception e) {
                    throw new IOException("Error: Could not read file " + file.getName() + ".");
                }
                accountsToTransactions.put(account, transactions);
            }
        }
    }

    void writeAccount(String account) throws IOException {
        if (accountsToTransactions.containsKey(account)) {
            if (accountsToTransactions.get(account).isEmpty()) {
                Path path = Paths.get(directoryName + "/" + account + ".json");
                String empty = "";
                Files.createDirectories(path.getParent());
                Files.writeString(path, empty);
                return;
            }
            String json = gson.toJson(accountsToTransactions.get(account));
            Path path = Paths.get(directoryName + "/" + account + ".json");
            Files.createDirectories(path.getParent());
            Files.writeString(path, json);
        }
    }
}


