import java.util.Iterator;

/**
 * Your randomized queue implementation must support each randomized queue
 * operation (besides creating an iterator) in constant amortized time and use
 * space proportional to the number of items currently in the queue. That is,
 * any sequence of M randomized queue operations (starting from an empty queue)
 * should take at most cM steps in the worst case, for some constant c.
 * Additionally, your iterator implementation must support construction in time
 * linear in the number of items and it must support the operations next() and
 * hasNext() in constant worst-case time; you may use a linear amount of extra
 * memory per iterator. The order of two or more iterators to the same
 * randomized queue should be mutually independent; each iterator must maintain
 * its own random order. use array
 * 
 * @author ivostoy
 * 
 * @param <Item>
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] m;
    private int mSize;
    private int mCapacity;

    // construct an empty randomized queue
    public RandomizedQueue() {
        resize(1);
    }

    // is the queue empty?
    public boolean isEmpty() {
        return mSize == 0;
    }

    @SuppressWarnings("unchecked")
    private void resize(int capacity) {
        // StdOut.println("resize " + capacity);
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < mSize; i++) {
            copy[i] = m[i];
        }
        m = copy;
        mCapacity = capacity;
    }

    // return the number of items on the queue
    public int size() {
        return mSize;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException("null input");
        }
        if (mSize == mCapacity) {
            // double array size
            resize(2 * mCapacity);
        }
        m[mSize] = item;
        mSize++;
    }

    // delete and return a random item
    public Item dequeue() {
        if (isEmpty())
            throw new java.util.NoSuchElementException("Empty");
        int idx = StdRandom.uniform(mSize);
        Item item = m[idx];
        // don't loiter
        m[idx] = null;
        // shift left
        for (int i = idx; i < mSize - 1; i++) {
            m[i] = m[i + 1];
        }
        m[mSize - 1] = null;
        mSize--;
        if (mSize > 0 && mSize == m.length / 4) {
            // shrink
            resize(m.length / 2);
        }
        return item;
    }

    // return (but do not delete) a random item
    public Item sample() {
        if (isEmpty())
            throw new java.util.NoSuchElementException("Empty");
        int idx = StdRandom.uniform(mSize);
        Item item = m[idx];
        return item;
    }

    // return an independent iterator over items in random order
    @Override
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        private int idx = 0;
        // shuffled copy of items
        private Item[] s;

        @SuppressWarnings("unchecked")
        ArrayIterator() {
            s = (Item[]) new Object[mSize];
            // copy
            for (int i = 0; i < mSize; i++) {
                s[i] = m[i];
            }
            // shuffle
            for (int i = 0; i < mSize; i++) {
                int ridx = StdRandom.uniform(i + 1);
                if (i == ridx)
                    continue;
                // swap with random index
                // StdOut.println("i " + i + ", ridx " + ridx +
                // " : swap " + s[i] + " and " + s[ridx]);
                Item item = s[i];
                s[i] = s[ridx];
                s[ridx] = item;
            }
        }

        @Override
        public boolean hasNext() {
            return idx < mSize;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException("no next");
            }
            Item item = s[idx];
            idx++;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing
    public static void main(String[] args) {
    }

}
