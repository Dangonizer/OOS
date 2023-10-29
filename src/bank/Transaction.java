package bank;

public abstract class Transaction implements CalculateBill {
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


    Transaction() {
    }

    //Konstruktor mit minimalen Angaben (Datum, Betrag, Beschreibung)
    public Transaction(String date, double amount, String description) {
        this.setDate(date);
        this.setAmount(amount);
        this.setDescription(description);
    }

    @Override
    public String toString() {
        return "date='" + date + '\'' +
                ", amount=" + amount +
                ", description='" + description + '\'';
    }

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

