package bank;

import bank.exceptions.*;

import java.util.*;

public class PrivateBank implements Bank {
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
        if (incomingInterest < 0 || incomingInterest > 1) {
            System.out.println("Error: No incoming interest rates bellow 0 and above 100% allowed for transfers.");
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
        if (outgoingInterest < 0 || outgoingInterest > 1) {
            System.out.println("Error: No outgoing interest rates bellow 0 and above 100% allowed for transfers.");
            return;
        }
        this.outgoingInterest = outgoingInterest;
    }

    /**
     * Instantiates object.
     *
     * @param name             Name of bank
     * @param incomingInterest Incoming interest rate
     * @param outgoingInterest Outgoing interest rate
     */
    public PrivateBank(String name, double incomingInterest, double outgoingInterest) {
        this.name = name;
        this.incomingInterest = incomingInterest;
        this.outgoingInterest = outgoingInterest;
    }

    /**
     * Copy Constructor
     *
     * @param bank Object to be copied
     */
    public PrivateBank(PrivateBank bank) {
        this(bank.name, bank.incomingInterest, bank.outgoingInterest);
    }


    @Override
    public String toString() {
        return "PrivateBank{" +
                "name='" + name + '\'' +
                ", incomingInterest=" + incomingInterest +
                ", outgoingInterest=" + outgoingInterest +
                ", accountsToTransactions=" + accountsToTransactions +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrivateBank that = (PrivateBank) o;
        return Double.compare(that.incomingInterest, incomingInterest) == 0 && Double.compare(that.outgoingInterest, outgoingInterest) == 0 && Objects.equals(name, that.name) && Objects.equals(accountsToTransactions, that.accountsToTransactions);
    }

/*    @Override
    public int hashCode() {
        return Objects.hash(name, incomingInterest, outgoingInterest, accountsToTransactions);
    }*/


    /**
     * Adds an account to the bank.
     *
     * @param account the account to be added
     * @throws AccountAlreadyExistsException if the account already exists
     */
    @Override
    public void createAccount(String account) throws AccountAlreadyExistsException {
        if (accountsToTransactions.containsKey(account))
            throw new AccountAlreadyExistsException("Error: Account already exists.");
        else {
            List<Transaction> list = new ArrayList<Transaction>();
            accountsToTransactions.put(account, list);
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
    public void createAccount(String account, List<Transaction> transactions) throws AccountAlreadyExistsException, TransactionAlreadyExistException, TransactionAttributeException {
        createAccount(account);
        for (Transaction t : transactions) {
            try {
                addTransaction(account, t);
            } catch (AccountDoesNotExistException e) {
                e.printStackTrace();
            }
        }
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
    public void addTransaction(String account, Transaction transaction) throws TransactionAlreadyExistException, AccountDoesNotExistException, TransactionAttributeException {

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
    public void removeTransaction(String account, Transaction transaction) throws AccountDoesNotExistException, TransactionDoesNotExistException {
        if (!accountsToTransactions.containsKey(account))
            throw new AccountDoesNotExistException("Error: Account " + account + " does not exist.");
        else if (!accountsToTransactions.get(account).contains(transaction))
            throw new TransactionDoesNotExistException("Error: Transaction " + transaction + " does not exist.");
        else accountsToTransactions.get(account).remove(transaction);
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
        return 0;
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
        List<Transaction> transactions = new ArrayList<>(getTransactions(account));
        if (transactions.isEmpty())
            return getTransactions(account);
        else {
            transactions.sort(Comparator.comparingDouble(Transaction::calculate));
            if (!asc) {
                transactions.reversed();
            }
            return transactions;
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
        return null;
    }
}
