import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {

    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        int pointer = 0;
        RandomizedQueue<String> rQueue = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String str = StdIn.readString();
            if (StdRandom.uniform(pointer + 1) < k) {
                if (rQueue.size() >= k)
                    rQueue.dequeue();
                rQueue.enqueue(str);
            }
            pointer++;
        }
        for (int i = 0; i < k; i++) {
            StdOut.println(rQueue.dequeue());
        }
    }

}
