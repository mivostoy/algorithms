import java.util.Iterator;

/**
 * 
 * 
 * Throw a NullPointerException if the client attempts to add a null item; throw
 * a java.util.NoSuchElementException if the client attempts to remove an item
 * from an empty deque; throw an UnsupportedOperationException if the client
 * calls the remove() method in the iterator; throw a
 * java.util.NoSuchElementException if the client calls the next() method in the
 * iterator and there are no more items to return.
 * 
 * Your deque implementation must support each deque operation in constant
 * worst-case time and use space proportional to the number of items currently
 * in the deque. Additionally, your iterator implementation must support the
 * operations next() and hasNext() (plus construction) in constant worst-case
 * time and use a constant amount of extra space per iterator. use linked list
 * 
 * @author ivostoy
 * 
 * @param <Item>
 */
public class Deque<Item> implements Iterable<Item> {
    // references to head and tail
    private Node head = null;
    private Node tail = null;
    private int len = 0;

    private class Node {
        Item item;
        Node next;
        Node prev;

        Node(Item item) {
            this.item = item;
        }
    }

    // construct an empty deque
    public Deque() {
    }

    // is the deque empty?
    public boolean isEmpty() {
        return len == 0;
    }

    // return the number of items on the deque
    public int size() {
        return len;
    }

    private void debug() {
        StdOut.print("[ ");
        if (head != null)
            StdOut.print(" head " + head.item);
        if (tail != null)
            StdOut.print(" tail " + tail.item);
        StdOut.print(" ] ");
        if (head != null) {
            for (Node n = head; n != null; n = n.next)
                StdOut.print(" " + n.item);
        }
        StdOut.print(" | ");
        if (tail != null) {
            for (Node n = tail; n != null; n = n.prev)
                StdOut.print(" " + n.item);
        }

        StdOut.println("");
    }

    // insert the item at the front
    public void addFirst(Item item) {
        if (item == null)
            throw new NullPointerException("input is null");
        // StdOut.println("addFirst " + item);
        Node n = new Node(item);
        n.next = head;
        head = n;
        if (isEmpty()) {
            tail = n;
        } else {
            head.next.prev = n;
        }
        // debug();
        len++;
    }

    // insert the item at the end
    public void addLast(Item item) {
        if (item == null)
            throw new NullPointerException("input is null");
        // StdOut.println("addLast " + item);
        Node n = new Node(item);
        n.prev = tail;
        tail = n;
        if (isEmpty()) {
            head = n;
        } else {
            tail.prev.next = n;
        }
        // debug();
        len++;
    }

    // delete and return the item at the front
    public Item removeFirst() {
        if (isEmpty())
            throw new java.util.NoSuchElementException("Empty");
        Item item = head.item;
        // StdOut.println("removeFirst " + item);
        // last elem?
        if (size() == 1) {
            // deloiter
            tail.item = null;
            tail = null;
        } else {
            head.next.prev.item = null;
            head.next.prev = null;
        }
        head = head.next;
        len--;
        // debug();
        return item;
    }

    // delete and return the item at the end
    public Item removeLast() {
        if (isEmpty())
            throw new java.util.NoSuchElementException("Empty");
        Item item = tail.item;
        // StdOut.println("removeLast " + item);
        // last elem?
        if (size() == 1) {
            head.item = null;
            head = null;
        } else {
            tail.prev.next.item = null;
            tail.prev.next = null;
        }
        tail = tail.prev;
        len--;
        // debug();
        return item;
    }

    @Override
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    // unit testing
    public static void main(String[] args) {
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException("no next");
            }
            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

}
