import bank.IncomingTransfer;
import bank.OutgoingTransfer;
import bank.Transfer;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link Transfer} class
 * @version 1.0
 * @author Kevin Schier
 */
public class TransferTest {

    Transfer t1;
    Transfer t2;
    @BeforeEach
    public void init() {
        t1 = new Transfer("01.01.2000", 100, "Test", "Sender", "Recipient");
        t2 = new Transfer("01.01.2000", 100, "Test");
    }

    @Test
    public void testConstructor() {
        assertEquals("01.01.2000", t1.getDate());
        assertEquals(100, t1.getAmount());
        assertEquals("Test", t1.getDescription());
        assertEquals("Sender", t1.getSender());
        assertEquals("Recipient", t1.getRecipient());
        assertEquals("01.01.2000", t2.getDate());
        assertEquals(100, t2.getAmount());
        assertEquals("Test", t2.getDescription());
        assertEquals("", t2.getSender());
        assertEquals("", t2.getRecipient());
    }

    @Test
    public void testCopyConstructor() {
        Transfer t3 = new Transfer(t1);
        assertEquals(t1, t3);
        t3.setAmount(42);
        assertNotEquals(t1, t3);
    }

    @Test
    public void testSetAmount() {
        t1.setAmount(50);
        assertEquals(50, t1.getAmount());
        t1.setAmount(-50);
        assertEquals(50, t1.getAmount());
    }

    @Test
    public void testEquals() {
        assertNotEquals(t1, t2);
        t2.setSender("Sender");
        t2.setRecipient("Recipient");
        assertEquals(t1, t2);
    }

    @Test
    public void testCalculate() {
        IncomingTransfer inc = new IncomingTransfer(t1);
        OutgoingTransfer out = new OutgoingTransfer(t1);
        assertEquals(100, inc.calculate(), 0.001);
        assertEquals(-100, out.calculate(), 0.001);
    }

    @Test
    public void testToString() {
        String expected =
                "date='01.01.2000', amount=100.0, description='Test', sender='Sender', recipient='Recipient'";
        assertEquals(expected, t1.toString());
    }

}
