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
 * time and use a constant amount of extra space per iterator.
 * use linked list
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

    void debug() {
        StdOut.print("[ ");
        if (head != null) 
            StdOut.print(" head " + head.item);
        if (tail != null)
            StdOut.print(" tail " + tail.item);
        StdOut.println(" ]");
    }
    // insert the item at the front
    public void addFirst(Item item) {
        if (item == null)
            throw new NullPointerException("input is null");
        Node n = new Node(item);
        n.next = head;
        head = n;
        if (isEmpty()) {
            tail = n;
        } else {
            head.prev = n;
        }
        len++;
    }

    // insert the item at the end
    public void addLast(Item item) {
        if (item == null)
            throw new NullPointerException("input is null");
        Node n = new Node(item);
        n.prev = tail;
        tail = n;
        if (isEmpty()) {
            head = n;
        } else {
            head.next = n;
        }
        len++;
    }

    // delete and return the item at the front
    public Item removeFirst() {
        if (isEmpty())
            throw new java.util.NoSuchElementException("Empty");
        Item item = head.item;
        // last elem?
        if (size() == 1) {
            tail = null;
        } else {
            head.next.prev = null;
        }
        head = head.next;    
        len--;
        return item;
    }

    // delete and return the item at the end
    public Item removeLast() {
        if (isEmpty())
            throw new java.util.NoSuchElementException("Empty");
        Item item = tail.item;
        if (size() == 1) {
            head = null;
        } else {
            tail.prev.next = null;
        }
        tail = tail.prev;    
        len--;
        return item;
    }
    

    @Override
    public Iterator<Item> iterator() {
        return null;
    }

    // unit testing
    public static void main(String[] args) {
    }


}
