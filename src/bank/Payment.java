package bank;

//Repräsentiert Ein und Auszahlungen
public class Payment {
	//Datum als string in Format DD.MM.YYYY
	private String date;
	//Betrag der Zahlung. Sowohl positive als auch negative Werte erlaubt
	private double amount;
	//Beschreibung der Zahlung
	private String description;
	//Zinsen bei Einzahlung als Dezimalwert. Erlaubter Wertebereich 0-1.
	private double incomingInterest;
	//Zinsen bei Auszahlung als Dezimalwert. Erlaubter Wertebereich 0-1.
	private double outgoingInterest;

	//Public Getter für das Datum
	public String getDate() {
		return date;
	}
	//Public Setter für das Datum
	public void setDate(String date) {
		this.date = date;
	}
	//Public Getter für den Zahlungsbetrag
	public double getAmount() {
		return amount;
	}
	//Public Setter für das Zahlungsbetrag
	public void setAmount(double amount) {
		this.amount = amount;
	}
	//Public Getter für die Beschreibung
	public String getDescription() {
		return description;
	}
	//Public Setter für die Beschreibung
	public void setDescription(String description) {
		this.description = description;
	}
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
	//Konstruktor mit minimalen Angaben (Datum, Betrag, Beschreibung)
	public Payment(String date, double amount, String description) {
		this.date = date;
		this.amount = amount;
		this.description = description;
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
		return "Payment{" +
				"date='" + date + '\'' +
				", amount=" + amount +
				", description='" + description + '\'' +
				", incomingInterest=" + incomingInterest +
				", outgoingInterest=" + outgoingInterest +
				'}';
	}
	//Funktion schreibt alle Attribute als Output in die Konsole
	public void printObject() {
		System.out.print(toString());
	}
}
