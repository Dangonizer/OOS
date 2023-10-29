import bank.*;

import java.util.Objects;

//Main Klasse und Methode zum Ausführen der Tests
public class Main {
    public static void main(String[] args) {

        //Tests für Payment Klasse
        System.out.println("Payment Tests:");

        //Test für minimalen Konstruktor
        Payment p1 = new Payment("01.01.2000", 100, "Constructor with 3 parameters");
        System.out.println(p1.toString());
        System.out.println();

        //Test für vollständigen Konstruktor
        Payment p2 = new Payment("01.01.2000", -100, "Constructor with all parameters", 0.3, 0.4);
        System.out.println(p2.toString());
        System.out.println();

        //Test für Calculate
        System.out.println(p2.calculate());
        p2.setAmount(300);
        System.out.println(p2.toString());
        System.out.println(p2.calculate());
        System.out.println();

        //Test für Copy Konstruktor
        Payment p3 = new Payment(p2);
        p2.setAmount(200);
        System.out.println(p3.toString());
        System.out.println();

        //Test für equals
        if (!(p3.equals(p2)))
            System.out.println("False");
        p2.setAmount(300);
        if (p3.equals(p2))
            System.out.println("True");
        System.out.println();

        //Test für Sanity Checks
        Payment p4 = new Payment("01.01.2000", 100, "Test 5", 2, 2);
        System.out.println(p4.toString());


        //Tests für Transfer Klasse
        System.out.println();
        System.out.println();
        System.out.println("Transfer Tests:");
        //Test für minimalen Konstruktor
        Transfer t1 = new Transfer("01.01.2000", 100, "Test 1");


        //Test für vollständigen Konstruktor
        Transfer t2 = new Transfer("01.01.2000", 100, "Test 2", "sender", "recipient");

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

        Transfer t6 = new Transfer("01.01.2000", 100, "test");
        Transfer t7 = new Transfer("01.01.2000", 100, "test");
        if (t6.equals(t7))
            System.out.println("True");
        t7.setAmount(200);
        if (t6.equals(t7))
            System.out.println("True");
        else System.out.println("False");
    }
}
