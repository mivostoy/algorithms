import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RandomizedQueueTest {

    @Test
    public void test1() {
        // String s;
        RandomizedQueue<String> q = new RandomizedQueue<String>();
        assertEquals(0, q.size());

        q.enqueue("A");
        q.enqueue("B");
        q.enqueue("C");
        q.enqueue("D");
        q.enqueue("E");
        assertEquals(5, q.size());
        // for (int i=1; i<10; i++) {
        // s = q.sample();
        // StdOut.println("sample " + s);
        // }
    }

    @Test
    public void testIterator() {
        RandomizedQueue<String> q = new RandomizedQueue<String>();
        // Iterator<String> it = q.iterator();
        // assertFalse(it.hasNext());
        q.enqueue("A");
        // assertTrue(it.hasNext());
        q.enqueue("B");
        q.enqueue("C");
        q.enqueue("D");
        q.enqueue("E");
        StdOut.println("");
        for (String s : q) {
            StdOut.print(s);
        }
        StdOut.println("");
        for (String s : q) {
            StdOut.print(s);
        }
        StdOut.println("");
        for (String s : q) {
            StdOut.print(s);
        }
    }

}
