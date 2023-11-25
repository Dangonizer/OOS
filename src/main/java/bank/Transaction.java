package bank;

/**
 * Superclass for {@link Payment} and {@link Transfer}. Implements {@link CalculateBill}.
 *
 * @author Kevin Schier
 * @version 1.0
 */
public abstract class Transaction implements CalculateBill {
    /**
     * Date of transaction in format DD.MM.YYYY
     */
    String date;

    /**
     * Amount of transaction
     */
    double amount;

    /**
     * Description of transaction
     */
    String description;


    /**
     * Returns date
     *
     * @return Date in format DD.MM.YYYY
     */
    public String getDate() {
        return date;
    }

    /**
     * Returns amount
     *
     * @return Returns amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Returns description
     *
     * @return Returns description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets date
     *
     * @param date Date in format DD.MM.YYYY
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Sets amount
     *
     * @param amount Amount of the transaction
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Sets description
     *
     * @param description Text describing transaction
     */
    public void setDescription(String description) {
        this.description = description;
    }


    /**
     * Default Constructor
     */
    Transaction() {
    }

    /**
     * Full constructor that sets all attributes
     *
     * @param date        Date in "DD.MM:YYYY" format
     * @param amount      Amount of the transaction
     * @param description Description of transaction
     */
    public Transaction(String date, double amount, String description) {
        this.setDate(date);
        this.setAmount(amount);
        this.setDescription(description);
    }

    /**
     * Returns all attributes as string
     *
     * @return String of attributes
     */
    @Override
    public String toString() {
        return "date='" + date + '\'' +
                ", amount=" + amount +
                ", description='" + description + '\'';
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
        if (!(o instanceof Transaction)) {
            return false;
        }
        Transaction t = (Transaction) o;
        return date.equals(t.date) && amount == t.amount && description.equals(t.description);
    }
}

