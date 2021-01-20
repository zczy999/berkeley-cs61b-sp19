package es.datastructur.synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(10);
        assertEquals(arb.isEmpty(),true);
        arb.enqueue(1);
        assertEquals(arb.capacity(),10);
        arb.enqueue(2);
        assertEquals(arb.peek(),(Integer) 1);
        arb.dequeue();
        assertEquals(arb.dequeue(),(Integer) 2);
        for (int i = 0; i < 10; i++) {
            arb.enqueue(i);
        }
//        assertEquals(arb.dequeue(), (Integer) 0);
        for (int i: arb) {
            int tem = (int) i;
            System.out.println(i);
        }
    }

    @Test
    public void TestEqual() {
        ArrayRingBuffer<Integer> arb1 = new ArrayRingBuffer(10);
        ArrayRingBuffer<Integer> arb2 = new ArrayRingBuffer(10);
        ArrayRingBuffer<Integer> arb3 = new ArrayRingBuffer(10);

        arb1.enqueue(5);
        arb2.enqueue(5);
        arb3.enqueue(5);

        arb1.enqueue(10);
        arb2.enqueue(10);
        arb3.enqueue(10);

        arb3.dequeue();

        arb2.dequeue();
        arb2.dequeue();
        arb2.enqueue(5);
        arb2.enqueue(10);

        assertTrue(arb1.equals(arb2));
        assertFalse(arb1.equals(arb3));
        assertTrue(arb1.equals(arb1));
    }
}
