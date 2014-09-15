import static org.junit.Assert.*;

import org.junit.Test;

/** FIXME
 * 
 * 
Test 3: Calls to addFirst() and removeLast()
  *     5 random calls (p1 = 0.9, p2 = 0.0, p3 = 0.0, p4 = 0.1)
  *    50 random calls (p1 = 0.9, p2 = 0.0, p3 = 0.0, p4 = 0.1)
     java.lang.NullPointerException
     Deque.removeLast(Deque.java:126)
     TestDeque.random(TestDeque.java:76)
     TestDeque.test3(TestDeque.java:135)
     TestDeque.main(TestDeque.java:553)
 * 
 * @author ivostoy
 *
 */
public class DequeTest {

    @Test
    public void testAddFirst() {
        Deque<String> d = new Deque<String>();
        assert(d.size() == 0);
        d.addFirst("foo");
        assertEquals(1, d.size());
    }

    @Test
    public void testAddLast() {
        Deque<String> d = new Deque<String>();
        d.addLast("foo");
        assertEquals(1, d.size());
    }

    @Test
    public void testRemoveFirst() {
        Deque<String> d = new Deque<String>();
        d.addFirst("foo");
        String s = d.removeFirst();
        assertEquals("foo", s);
        assertEquals(0, d.size());
    }

    @Test
    public void testRemoveLast() {
        Deque<String> d = new Deque<String>();
        d.addLast("foo");
        String s = d.removeLast();
        assertEquals("foo", s);
        assertEquals(0, d.size());
    }

    @Test
    public void testMulti1() {
        String s;
        Deque<String> d = new Deque<String>();
        d.addFirst("foo");
        d.addFirst("bar");
        s = d.removeFirst();
        assertEquals("bar", s);
        s = d.removeFirst();
        assertEquals("foo", s);
    }

    @Test
    public void testMulti2() {
        String s;
        Deque<String> d = new Deque<String>();
        d.addLast("foo");
        d.addLast("bar");
        s = d.removeLast();
        assertEquals("bar", s);
        s = d.removeLast();
        assertEquals("foo", s);
    }
    @Test
    public void testMulti3() {
        String s;
        Deque<String> d = new Deque<String>();
        d.addFirst("foo");
        d.addLast("bar");
        s = d.removeLast();
        assertEquals("bar", s);
        s = d.removeFirst();
        assertEquals("foo", s);
    }

    @Test
    public void testIterator() {
        Deque<String> d = new Deque<String>();
        assertFalse(d.iterator().hasNext());
        d.addFirst("bbb");
        assertTrue(d.iterator().hasNext());
        d.addLast("ccc");
        d.addFirst("aaa");
        d.addLast("ddd");
        for (String s: d) {
           StdOut.println(s); 
        }
    }
    
}
