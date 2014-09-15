/**
 * Subset takes a command-line integer k; reads in a sequence of N strings from
 * standard input using StdIn.readString(); and prints out exactly k of them,
 * uniformly at random. Each item from the sequence can be printed out at most
 * once. You may assume that k ≥ 0 and no greater than the number of string N on
 * standard input.
 * 
 * % echo A B C D E F G H I | java Subset 3
 * 
 * The running time of Subset must be linear in the size of the input. You may
 * use only a constant amount of memory plus either one Deque or RandomizedQueue
 * object of maximum size at most N, where N is the number of strings on
 * standard input. (For an extra challenge, use only one Deque or
 * RandomizedQueue object of maximum size at most k.)
 * zip Queues.zip Deque.java RandomizedQueue.java Subset.java
 * @author ivostoy
 * 
 */
public class Subset {

    public static void main(String[] args) {
        if (args.length < 1) {
            StdOut.println("Usage: subset k");
            return;
        }
        int k = Integer.parseInt(args[0]);
        if (k <= 0) 
            return;
        
        RandomizedQueue<String> q = new RandomizedQueue<String>();
        //int n = 0;
        while (StdIn.hasNextLine()) {
            String s = StdIn.readString();
            // StdOut.println(s + " " + s.isEmpty());
            if (s.equals("-"))
                break;
            q.enqueue(s);
        }
//        if (n < k) {
//            StdOut.println("need more strings than " + k + " - got " + n);
//            return;
//        }

        for (String s : q) {
            StdOut.println(s);
            k--;
            if (k == 0)
                break;
        }
    }

}
