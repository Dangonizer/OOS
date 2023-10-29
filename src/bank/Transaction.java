package bank;

public abstract class Transaction {
    //Datum als string in Format DD.MM.YYYY
    String date;
    //Betrag der Zahlung. Sowohl positive als auch negative Werte erlaubt
    double amount;
    //Beschreibung der Zahlung
    String description;

    //Public Getter für das Datum
    public String getDate() {
        return date;
    }

    //Public Getter für den Betrag
    public double getAmount() {
        return amount;
    }

    //Public Getter für die Beschreibung
    public String getDescription() {
        return description;
    }

    //Public Setter für das Datum
    public void setDate(String date) {
        this.date = date;
    }

    //Public Setter für den Betrag
    public void setAmount(double amount) {
        this.amount = amount;
    }

    //Public Setter für die Beschreibung
    public void setDescription(String description) {
        this.description = description;
    }
}

