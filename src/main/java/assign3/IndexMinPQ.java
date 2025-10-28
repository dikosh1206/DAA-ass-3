package assign3;

import java.util.NoSuchElementException;

public class IndexMinPQ<Key extends Comparable<Key>> {
    private int maxN;
    private int n;
    private int[] pq;       // binary heap using 1-based indexing: pq[1] is min index
    private int[] qp;       // inverse: qp[index] = position of index in pq (0 if not present)
    private Key[] keys;     // keys[index] = priority

    @SuppressWarnings("unchecked")
    public IndexMinPQ(int maxN) {
        this.maxN = maxN;
        keys = (Key[]) new Comparable[maxN];
        qp = new int[maxN];
        pq = new int[maxN + 1];
        for (int i = 0; i < maxN; i++) qp[i] = -1;
    }

    public boolean isEmpty() { return n == 0; }

    public boolean contains(int i) {
        return qp[i] != -1;
    }

    public void insert(int i, Key key) {
        if (contains(i)) throw new IllegalArgumentException("index is already in pq");
        n++;
        qp[i] = n;
        pq[n] = i;
        keys[i] = key;
        swim(n);
    }

    public int delMin() {
        if (n == 0) throw new NoSuchElementException();
        int min = pq[1];
        exch(1, n--);
        sink(1);
        qp[min] = -1;
        keys[min] = null;
        pq[n+1] = -1;
        return min;
    }

    public void decreaseKey(int i, Key key) {
        if (!contains(i)) throw new NoSuchElementException();
        if (keys[i].compareTo(key) <= 0) throw new IllegalArgumentException("Calling decreaseKey() with wrong key");
        keys[i] = key;
        swim(qp[i]);
    }

    private boolean greater(int i, int j) {
        return keys[pq[i]].compareTo(keys[pq[j]]) > 0;
    }

    private void exch(int i, int j) {
        int swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
        qp[pq[i]] = i;
        qp[pq[j]] = j;
    }

    private void swim(int k) {
        while (k > 1 && greater(k/2, k)) {
            exch(k, k/2);
            k = k/2;
        }
    }

    private void sink(int k) {
        while (2*k <= n) {
            int j = 2*k;
            if (j < n && greater(j, j+1)) j++;
            if (!greater(k, j)) break;
            exch(k, j);
            k = j;
        }
    }
}
