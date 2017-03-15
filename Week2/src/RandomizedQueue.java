import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int n;
    private Item[] itemList;

    // construct an empty randomized queue
    public RandomizedQueue() {
        itemList = (Item[]) new Object[1];
        n = 0;
    }

    // is the queue empty?
    public boolean isEmpty() {
        return n <= 0;
    }

    // return the number of items on the queue
    public int size() {
        return n;
    }

    private void resize(int capacity) {
        Item[] itemListTmp = (Item[]) new Object[capacity];
        int length;
        if (itemList.length < itemListTmp.length)
            length = itemList.length;
        else {
            length = itemListTmp.length;
        }
        for (int i = 0; i < length; i++) {
            itemListTmp[i] = itemList[i];
        }
        itemList = itemListTmp;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new NullPointerException("Item can't be null!");
        if (n >= itemList.length)
            resize(itemList.length * 2);
        itemList[n++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException("The queue is empty!");
        int randomInt = StdRandom.uniform(0, n);
        Item itemOut = itemList[randomInt];
        itemList[randomInt] = itemList[--n];
        itemList[n] = null;
        if (n <= itemList.length / 4 && itemList.length >= 2)
            resize(itemList.length / 2);
        return itemOut;
    }

    // return (but do not remove) a random item
    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException("The queue is empty!");
        int randomInt = StdRandom.uniform(0, n);
        return itemList[randomInt];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private Item[] itemListIte;
        private int nIte;

        public RandomizedQueueIterator() {
            itemListIte = (Item[]) new Object[n];
            nIte = n;
            for (int i = 0; i < n; i++) {
                itemListIte[i] = itemList[i];
            }
        }

        @Override
        public boolean hasNext() {
            return nIte > 0;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            int randomInt = StdRandom.uniform(0, nIte);
            Item itemOut = itemListIte[randomInt];
            itemListIte[randomInt] = itemListIte[--nIte];
            return itemOut;
        }

    }

    public static void main(String[] args) {

    }

}
