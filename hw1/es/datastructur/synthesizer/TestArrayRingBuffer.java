package es.datastructur.synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer arb = new ArrayRingBuffer(10);
        assertEquals(arb.isEmpty(),true);
        arb.enqueue(1);
        assertEquals(arb.capacity(),10);
        arb.enqueue(2);
        assertEquals(arb.peek(),1);
        arb.dequeue();
        assertEquals(arb.dequeue(),2);
        for (int i = 0; i < 10; i++) {
            arb.enqueue(i);
        }
        assertEquals(arb.dequeue(), 0);;
    }
}
