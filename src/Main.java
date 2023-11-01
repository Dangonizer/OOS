import bank.*;
import jdk.jfr.StackTrace;

import java.util.Objects;

/**
 * Main class for testing functionality
 *
 * @author Kevin Schier
 * @version 1.1
 */
public class Main {
    public static void main(String[] args) {

        //Tests für Payment Klasse
        System.out.println("Payment Tests:");

        //Test für minimalen Konstruktor
        Payment p1 = new Payment("01.01.2000", 100, "Constructor with 3 parameters");
        System.out.println(p1);
        System.out.println();

        //Test für vollständigen Konstruktor
        Payment p2 = new Payment("01.01.2000", -100, "Constructor with all parameters", 0.3, 0.4);
        System.out.println(p2);
        System.out.println();

        //Test für Calculate
        System.out.println(p2.calculate());
        p2.setAmount(300);
        System.out.println(p2);
        System.out.println(p2.calculate());
        System.out.println();

        //Test für Copy Konstruktor
        Payment p3 = new Payment(p2);
        p2.setIncomingInterest(0.5);
        System.out.println(p3);
        System.out.println();

        //Test für equals
        if (!(p3.equals(p2)))
            System.out.println("False");
        p2.setIncomingInterest(0.3);
        if (p3.equals(p2))
            System.out.println("True");
        System.out.println();

        //Test für Sanity Checks
        Payment p4 = new Payment("01.01.2000", 100, "Sanity Check", 2, 2);
        System.out.println(p4);


        //Tests für Transfer Klasse
        System.out.println();
        System.out.println();
        System.out.println("Transfer Tests:");

        //Test für minimalen Konstruktor
        Transfer t1 = new Transfer("01.01.2000", 100, "Test 1");
        System.out.println(t1);
        System.out.println();

        //Test für vollständigen Konstruktor
        Transfer t2 = new Transfer("01.01.2000", 100, "Test 2", "sender", "recipient");
        System.out.println(t2);
        System.out.println();

        //Test für calculate
        System.out.println(t2.calculate());
        System.out.println();

        //Test für Copy Konstruktor
        Transfer t3 = new Transfer(t2);
        t2.setSender("wrong sender");
        System.out.println(t3);
        System.out.println();

        //Test für equals
        if (!(t3.equals(t2)))
            System.out.println("False");
        t2.setSender("sender");
        if (t3.equals(t2))
            System.out.println("True");
        System.out.println();

        //Test für Sanity Checks
        Transfer t4 = new Transfer("01.01.2000", -100, "Test 4");
        System.out.println(t4);


        PrivateBank b1 = new PrivateBank("Bank1", 0.5, 0.4);

        try {
            b1.createAccount("Account1");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
