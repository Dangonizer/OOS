import bank.Payment;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link Payment} class
 * @version 1.0
 * @author Kevin Schier
 */
public class PaymentTest {


    private Payment p1;
    private Payment p2;

    @BeforeEach
    public void init() {
        p1 = new Payment("01.01.2000", 100, "Test", 0.2, 0.1);
        p2 = new Payment("01.01.2000", -100, "Test");
    }

    @Test
    public void testConstructor() {
        assertEquals("01.01.2000", p1.getDate());
        assertEquals(100, p1.getAmount());
        assertEquals("Test", p1.getDescription());
        assertEquals(0.2, p1.getIncomingInterest());
        assertEquals(0.1, p1.getOutgoingInterest());
        assertEquals("01.01.2000", p2.getDate());
        assertEquals(-100, p2.getAmount());
        assertEquals("Test", p2.getDescription());
        assertEquals(0, p2.getIncomingInterest());
        assertEquals(0, p2.getOutgoingInterest());
    }

    @Test
    public void testCopyConstructor() {
        Payment p3 = new Payment(p1);
        assertEquals(p1, p3);
        p3.setAmount(42);
        assertNotEquals(p1, p3);
    }

    @Test
    public void testSetIncomingInterest() {
        p1.setIncomingInterest(0.3);
        assertEquals(0.3, p1.getIncomingInterest());
        p1.setIncomingInterest(1.1);
        assertEquals(0.3, p1.getIncomingInterest());
        p1.setIncomingInterest(-0.1);
        assertEquals(0.3, p1.getIncomingInterest());
    }

    @Test
    public void testSetOutgoingInterest() {
        p1.setOutgoingInterest(0.3);
        assertEquals(0.3, p1.getOutgoingInterest());
        p1.setOutgoingInterest(1.1);
        assertEquals(0.3, p1.getOutgoingInterest());
        p1.setOutgoingInterest(-0.1);
        assertEquals(0.3, p1.getOutgoingInterest());
    }

    @Test
    public void testEquals() {
        assertNotEquals(p1, p2);
        p1.setAmount(p2.getAmount());
        p1.setIncomingInterest(p2.getIncomingInterest());
        p1.setOutgoingInterest(p2.getOutgoingInterest());
        assertEquals(p1, p2);
    }

    @Test
    public void testCalculate() {
        assertEquals(80, p1.calculate(), 0.001);
        p2.setOutgoingInterest(0.3);
        assertEquals(-130, p2.calculate(), 0.001);
    }

    @Test
    public void testToString() {
        String expected =
                "date='01.01.2000', amount=100.0, description='Test', incomingInterest=0.2, outgoingInterest=0.1";
        assertEquals(expected, p1.toString());
    }

}

