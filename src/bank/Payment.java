package bank;

//Repräsentiert Ein und Auszahlungen
public class Payment extends Transaction {
    //
    private double incomingInterest;
    //Zinsen bei Auszahlung als Dezimalwert. Erlaubter Wertebereich 0-1.
    private double outgoingInterest;

    //Public Getter für die Einzahlungszinsen
    public double getIncomingInterest() {
        return incomingInterest;
    }

    //Public Setter für die Einzahlungszinsen. Checkt Nutzerinput auf valide Werte (0-1)
    public void setIncomingInterest(double incomingInterest) {
        if (incomingInterest < 0 || incomingInterest > 1) {
            System.out.println("Error: No incoming interest rates bellow 0 and above 100% allowed for transfers.");
            return;
        }
        this.incomingInterest = incomingInterest;
    }

    //Public Getter für die Auszahlungszinsen
    public double getOutgoingInterest() {
        return outgoingInterest;
    }

    //Public Setter für die Auszahlungszinsen. Checkt Nutzerinput auf valide Werte (0-1)
    public void setOutgoingInterest(double outgoingInterest) {
        if (outgoingInterest < 0 || outgoingInterest > 1) {
            System.out.println("Error: No outgoing interest rates bellow 0 and above 100% allowed for transfers.");
            return;
        }
        this.outgoingInterest = outgoingInterest;
    }


    //Minimal Constructor
    public Payment(String date, double amount, String description) {
        super(date, amount, description);
    }

    //Konstruktor mit vollen Angaben (Datum, Betrag, Beschreibung, Einzalungszinsen, Auszahlungszinsen)
    public Payment(String date, double amount, String description, double incomingInterest, double outgoingInterest) {
        this(date, amount, description);
        this.setIncomingInterest(incomingInterest);
        this.setOutgoingInterest(outgoingInterest);
    }

    //Copy Konstruktor
    public Payment(Payment payment) {
        this.date = payment.date;
        this.amount = payment.amount;
        this.description = payment.description;
        this.incomingInterest = payment.incomingInterest;
        this.outgoingInterest = payment.outgoingInterest;
    }

    //Funktion gibt alle Attribute des Objektes als String zurück
    @Override
    public String toString() {
        return super.toString() +
                ", incomingInterest=" + incomingInterest +
                ", outgoingInterest=" + outgoingInterest;
    }

    @Override
    public double calculate() {
        if (amount >= 0)
        {
            return amount - amount * incomingInterest;
        }
        else
            return amount + amount * outgoingInterest;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Payment)) {
            return false;
        }
        Payment p = (Payment) o;
        return super.equals((Transaction)o) && incomingInterest == p.incomingInterest && outgoingInterest == p.outgoingInterest;
    }
}
