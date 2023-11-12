import bank.*;
import jdk.jfr.StackTrace;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

/**
 * Main class for testing functionality
 *
 * @author Kevin Schier
 * @version 1.1
 */
public class Main {
    public static void main(String[] args) {

        System.out.println("Create transfer and payment to test sanity checks\n\n");

        Transfer t0 = new Transfer("01.01.2000", -200, "T1", "s1", "r1");
        Payment p0 = new Payment("01.01.2000", -200, "T1", 1.1, 1.2);

        System.out.println(t0);
        System.out.println(p0);


        System.out.println("\n\nCreate banks and test sanity checks + equals\n\n");

        PrivateBank b1 = new PrivateBank("Bank1", 0.5, 0.4);
        System.out.println(b1);
        PrivateBank b2 = new PrivateBank("Bank2", 2, 3);
        System.out.println(b2);
        b2.setIncomingInterest(0.5);
        b2.setOutgoingInterest(0.4);

        List <Transaction> t1 = new ArrayList<>();
        t1.add(new IncomingTransfer("01.01.2000",200,"T1","s1","r1"));

        try {
            b1.createAccount("Test1", t1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            b2.createAccount("Test1", t1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(b1.equals(b2));

        b2.setName("Bank1");

        System.out.println(b1.equals(b2));






        System.out.println("\n\nTest all methods of Private bank\n\n");

        //Create account
        try {
            b1.createAccount("Account1");
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(b1);

        try {
            b1.createAccount("Account1");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Create account with transactions
        try {
            b1.createAccount("Account2",t1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(b1);

        List <Transaction> t2 = new ArrayList<>();
        t2.add(new IncomingTransfer("01.01.2000",400,"T1","s2","r2"));

        try {
            b1.createAccount("Account2",t2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(b1);

        //Add transaction

        //No Account
        try {
            b1.addTransaction("Account3",new IncomingTransfer("01.01.2000",200,"T1","s1","r1"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(b1);

        //No Transaction
        try {
            b1.addTransaction("Account2",new IncomingTransfer("01.01.2000",200,"T1","s1","r1"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(b1);

        //Invalid Transaction
        try {
            b1.addTransaction("Account2",new Payment("01.01.2000",300,"P1",2,3));
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(b1);

        //Valid Transaction
        try {
            b1.addTransaction("Account2",new Payment("01.01.2000",300,"P2",0.8,0.6));
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(b1);

        //Duplicate Transaction
        try {
            b1.addTransaction("Account2",new Payment("01.01.2000",300,"P2",0.8,0.6));
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(b1);

















    }
}
