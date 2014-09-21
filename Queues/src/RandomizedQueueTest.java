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

    /*
 * 
 * Timing RandomizedQueue
*-----------------------------------------------------------
Running 12 total tests.

N random calls to: enqueue(), sample(), dequeue(), isEmpty(), and size()

                    N  seconds
----------------------------------
=> passed        1024     0.01
=> passed        4096     0.01
=> passed       16384     0.06
=> passed      128000     3.64

Total: 0/12 tests passed:Test aborted. Ran out of time or crashed before completion.
 */



    void timing(int N) {
        RandomizedQueue<Integer> q = new RandomizedQueue<Integer>();
        Stopwatch w;
        w = new Stopwatch();
        for (int i=0; i<N; i++) {
                q.enqueue(i);
        }
        StdOut.println(String.format("%d calls to enqueue() took %f sec ", N, w.elapsedTime()));
        assertEquals(N, q.size());

        w = new Stopwatch();
        for (int i=0; i<N; i++) {
            q.size();
        }
        StdOut.println(String.format("size() took %f ", w.elapsedTime()));

        w = new Stopwatch();
        for (int i=0; i<N; i++) {
            q.sample();
        }
        StdOut.println(String.format("sample() took %f ", w.elapsedTime()));
 
        w = new Stopwatch();
        for (int i=0; i<N; i++) {
            q.isEmpty();
        }
        StdOut.println(String.format("isempty() took %f ", w.elapsedTime()));
        for (int i=0; i<N; i++) {
            q.dequeue();
        }
        StdOut.println(String.format("dequeue() took %f ", w.elapsedTime()));

    }
    
    @Test
    public void testTime() {
        timing(1024);
        timing(10240);
        timing(128000);
        timing(256000);
        timing(1280000);
    }


}
