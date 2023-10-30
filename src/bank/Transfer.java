package bank;

/**
 * This class manages transfers.
 * @author Kevin Schier
 * @version 1.1
 */
public class Transfer extends Transaction {

    /**
     * Name of sender
     */
    private String sender;

    /**
     * Name of recipient
     */
    private String recipient;


    /**
     * Returns sender
     * @return Returns sender
     */
    public String getSender() {
        return sender;
    }

    /**
     * Returns recipient
     * @return Returns recipient
     */
    public String getRecipient() {
        return recipient;
    }

    /**
     * Sets amount if value is in allowed range(>=0), otherwise writes error to console and does not set value.
     * @param amount Amount that should be set (>=0)
     */
    @Override
    public void setAmount(double amount) {
        if (amount < 0) {
            System.out.println("Error: No negative amounts allowed for transfers.");
            return;
        }
        this.amount = amount;
    }

    /**
     * Sets sender
     * @param sender Recipient of transaction
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * Sets recipient
     * @param recipient Recipient of transaction
     */
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }


    /**
     * Constructor with minimal amount of parameters.
     * @param date Date in "DD.MM:YYYY" format
     * @param amount Amount of the payment
     * @param description Description of payment
     */
    public Transfer(String date, double amount, String description) {
        super(date, amount, description);
    }

    /**
     * Full constructor that sets all attributes
     * @param date Date in "DD.MM:YYYY" format
     * @param amount Amount of the payment
     * @param description Description of payment
     * @param sender Sender of the transfer
     * @param recipient Recipient of the transfer
     */
    public Transfer(String date, double amount, String description, String sender, String recipient) {
        this(date, amount, description);
        this.sender = sender;
        this.recipient = recipient;
    }

    /**
     * Copies content of parameter {@link Transfer} object to created object
     * @param transfer Object that will be copied
     */
    public Transfer(Transfer transfer) {
        this.date = transfer.date;
        this.amount = transfer.amount;
        this.description = transfer.description;
        this.sender = transfer.sender;
        this.recipient = transfer.recipient;
    }

    /**
     * Returns all attributes as string
     * @return String of attributes
     */
    @Override
    public String toString() {
        return super.toString() +
                ", sender='" + sender + '\'' +
                ", recipient='" + recipient + '\'';
    }

    /**
     * Returns amount
     * @return Returns {@link super#amount}
     */
    @Override
    public double calculate() {
        return amount;
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
        if (!(o instanceof Transfer)) {
            return false;
        }
        Transfer t = (Transfer) o;
        return super.equals((Transaction)o) && sender.equals(t.sender) && recipient.equals(t.recipient);
    }
}
