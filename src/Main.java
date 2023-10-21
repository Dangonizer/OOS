import bank.*;

import java.util.Objects;

//Main Klasse und Methode zum Ausführen der Tests
public class Main {
	public static void main(String[] args) {
		//Tests für Payment Klasse
		System.out.println("Payment KEK1:");
		//Test für minimalen Konstruktor
		Payment p1 = new Payment("01.01.2000", 100, "Test 1");

		if (Objects.equals(p1.toString(), "Payment{date='01.01.2000', amount=100.0, description='Test 1', incomingInterest=0.0, outgoingInterest=0.0}"))
			System.out.println("Constructor 1 Test2 passed successfully");
		else System.out.println("Constructor 1 Test failed");
		//Test für vollständigen Konstruktor
		Payment p2 = new Payment("01.01.2000", 100, "Test 2", 0.3, 0.4);

		if (Objects.equals(p2.toString(), "Payment{date='01.01.2000', amount=100.0, description='Test 2', incomingInterest=0.3, outgoingInterest=0.4}"))
			System.out.println("Constructor 2 Test passed successfully");
		else System.out.println("Constructor 2 Test failed");
		//Test für Copy Konstruktor
		Payment p3 = new Payment("01.01.2000", 100, "Test 3");
		Payment p4 = new Payment(p3);
		p3.setAmount(200);

		if (Objects.equals(p3.toString(), "Payment{date='01.01.2000', amount=200.0, description='Test 3', incomingInterest=0.0, outgoingInterest=0.0}")
				&& Objects.equals(p4.toString(), "Payment{date='01.01.2000', amount=100.0, description='Test 3', incomingInterest=0.0, outgoingInterest=0.0}"))
			System.out.println("Copy Constructor Test passed successfully");
		else System.out.println("Copy Constructor Test failed");
		//Test für Sanity Checks
		Payment p5 = new Payment("01.01.2000", 100, "Test 5", 2, 2);

		if (Objects.equals(p5.toString(), "Payment{date='01.01.2000', amount=100.0, description='Test 5', incomingInterest=0.0, outgoingInterest=0.0}"))
			System.out.println("Sanity Check Test passed successfully");
		else System.out.println("Sanity Check Test failed");

		//Tests für Transfer Klasse
		System.out.println("Transfer tests:");
		//Test für minimalen Konstruktor
		Transfer t1 = new Transfer("01.01.2000", 100, "Test 1");

		if (Objects.equals(t1.toString(), "Transfer{date='01.01.2000', amount=100.0, description='Test 1', sender='null', recipient='null'}"))
			System.out.println("Constructor 1 Test passed successfully");
		else System.out.println("Constructor 1 Test failed");
		//Test für vollständigen Konstruktor
		Transfer t2 = new Transfer("01.01.2000", 100, "Test 2","sender","recipient");

		if (Objects.equals(t2.toString(), "Transfer{date='01.01.2000', amount=100.0, description='Test 2', sender='sender', recipient='recipient'}"))
			System.out.println("Constructor 2 Test passed successfully");
		else System.out.println("Constructor 2 Test failed");
		//Test für Copy Konstruktor
		Transfer t3 = new Transfer("01.01.2000", 100, "Test 3");
		Transfer t4 = new Transfer(t3);
		t3.setAmount(200);

		if (Objects.equals(t3.toString(), "Transfer{date='01.01.2000', amount=200.0, description='Test 3', sender='null', recipient='null'}")
				&& Objects.equals(t4.toString(), "Transfer{date='01.01.2000', amount=100.0, description='Test 3', sender='null', recipient='null'}"))
			System.out.println("Copy Constructor Test passed successfully");
		else System.out.println("Copy Constructor Test failed");
		//Test für Sanity Checks
		Transfer t5 = new Transfer("01.01.2000", -100, "Test 5");

		if (Objects.equals(t5.toString(), "Transfer{date='01.01.2000', amount=0.0, description='Test 5', sender='null', recipient='null'}"))
			System.out.println("Sanity Check Test passed successfully");
		else System.out.println("Sanity Check Test failed");
	}
}
