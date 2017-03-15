import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int sizeQueue;

    private class Node {
        private Item value;
        private Node next;
        private Node prev;

        public Node(Item valuePara, Node nextPara, Node prevPara) {
            value = valuePara;
            next = nextPara;
            prev = prevPara;
        }
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = first;
        sizeQueue = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null && last == null;
    }

    // return the number of items on the deque
    public int size() {
        return sizeQueue;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null)
            throw new NullPointerException("Item shouldn't be null!");
        if (sizeQueue == 0) {
            first = new Node(item, null, null);
            last = first;
            sizeQueue++;
            return;
        }
        Node oldFirst = first;
        first = new Node(item, oldFirst, null);
        oldFirst.prev = first;
        sizeQueue++;
    }

    // add the item to the end
    public void addLast(Item item) {
        if (item == null)
            throw new NullPointerException("Item shouldn't be null!");
        if (sizeQueue == 0) {
            last = new Node(item, null, null);
            first = last;
            sizeQueue++;
            return;
        }
        Node oldLast = last;
        last = new Node(item, null, oldLast);
        oldLast.next = last;
        sizeQueue++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (sizeQueue <= 0)
            throw new NoSuchElementException("Can't remove element from an empty Deque!");
        Item firstItem = first.value;
        first = first.next;
        if (first != null)
            first.prev = null;
        sizeQueue--;
        if (sizeQueue == 0)
            last = first;
        return firstItem;
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (sizeQueue <= 0)
            throw new NoSuchElementException("Can't remove element from an empty Deque!");
        Item lastItem = last.value;
        last = last.prev;
        if (last != null)
            last.next = null;
        sizeQueue--;
        if (sizeQueue == 0)
            first = last;
        return lastItem;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {

        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            Item currentItem = current.value;
            current = current.next;
            return currentItem;
        }
    }

    public static void main(String[] args) {
    }

}
