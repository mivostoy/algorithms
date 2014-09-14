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
 * its own random order.
 * use array
 * @author ivostoy
 * 
 * @param <Item>
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    
    private Item[] m;
    private int mSize = 0;
    private int mCapacity = 0;
    // construct an empty randomized queue
    public RandomizedQueue() {
    }

    // is the queue empty?
    public boolean isEmpty() {
        return  mSize == 0;
    }

    @SuppressWarnings("unchecked")
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i=0; i<mSize; i++) {
            copy[i] = m[i];
        }
        m = copy;
    }
    // return the number of items on the queue
    public int size() {
        return mSize;
    }

    // add the item
    public void enqueue(Item item) {
        if (mSize == mCapacity) {
            // double array size
            mCapacity *= 2;
            resize(mCapacity);
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
        // shift left
        for (int i=idx; i<mSize-1; i++) {
            m[i] = m[i+1];
        }
        mSize--;
        return item;
    }

    // return (but do not delete) a random item
    public Item sample() {
        if (isEmpty())
            throw new java.util.NoSuchElementException("Empty");
        int idx = StdRandom.uniform(mSize);
        Item item = m[idx];
        mSize--;
        return item;
    }


    // return an independent iterator over items in random order
    @Override
    public Iterator<Item> iterator() {
        // TODO Auto-generated method stub
        return null;
    }

    // unit testing
    public static void main(String[] args) {
    }

}
