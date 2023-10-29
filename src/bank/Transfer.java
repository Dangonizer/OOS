package bank;

//Repräsentiert Überweisungen
public class Transfer extends Transaction {

    //Name des Senders
    private String sender;
    //Name des Empfängers
    private String recipient;


    public String getSender() {
        return sender;
    }

    //Public Getter für den Empfänger
    public String getRecipient() {
        return recipient;
    }

    //Public Setter für den Betrag. Nur positive Beträge sind erlaubt.
    @Override
    public void setAmount(double amount) {
        if (amount < 0) {
            System.out.println("Error: No negative amounts allowed for transfers.");
            return;
        }
        this.amount = amount;
    }

    //Public Setter für den Sender
    public void setSender(String sender) {
        this.sender = sender;
    }

    //Public Setter für den Empfänger
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }


    //Minimaler Konstruktor (Datum, Betrag, Beschreibung)
    public Transfer(String date, double amount, String description) {
        this.date = date;
        this.setAmount(amount);
        this.description = description;
    }

    //Vollständiger Konstruktor (Datum, Betrag, Beschreibung, Sender, Empfänger)
    public Transfer(String date, double amount, String description, String sender, String recipient) {
        this(date, amount, description);
        this.sender = sender;
        this.recipient = recipient;
    }

    //Copy Konstruktor
    public Transfer(Transfer transfer) {
        this.date = transfer.date;
        this.amount = transfer.amount;
        this.description = transfer.description;
        this.sender = transfer.sender;
        this.recipient = transfer.recipient;
    }

    //Funktion gibt alle Attribute des Objekts als String zurück
    @Override
    public String toString() {
        return "Transfer{" +
                "date='" + date + '\'' +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", sender='" + sender + '\'' +
                ", recipient='" + recipient + '\'' +
                '}';
    }
}
