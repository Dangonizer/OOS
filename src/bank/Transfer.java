package bank;

//Repräsentiert Überweisungen
public class Transfer {
	//Datum als string in Format DD.MM.YYYY
	private String date;
	//Betrag der Zahlung. Sowohl positive als auch negative Werte erlaubt
	private double amount;
	//Beschreibung der Zahlung
	private String description;
	//Name des Senders
	private String sender;
	//Name des Empfängers
	private String recipient;

	//Public Getter für den Betrag
	public double getAmount() {
		return amount;
	}
	//Public Getter für die Beschreibung
	public String getDescription() {
		return description;
	}
	//Public Getter für den Sender
	public String getSender() {
		return sender;
	}
	//Public Getter für den Empfänger
	public String getRecipient() {
		return recipient;
	}
	//Public Getter für das Datum
	public String getDate() {
		return date;
	}
	//Public Setter für das Datum
	public void setDate(String date) {
		this.date = date;
	}
	//Public Setter für den Betrag. Nur positive Beträge sind erlaubt.
	public void setAmount(double amount) {
		if (amount < 0) {
			System.out.println("Error: No negative amounts allowed for transfers.");
			return;
		}
		this.amount = amount;
	}
	//Public Setter für die Beschreibung
	public void setDescription(String description) {
		this.description = description;
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
	//Funktion gibt alle Attribute auf Konsole aus
	public void printObject() {
		System.out.print(toString());
	}

}
