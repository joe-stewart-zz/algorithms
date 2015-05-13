import java.util.*;

public class IndexMinPQ<Key extends Comparable<Key>> implements Iterable<Integer> {
    private int NMAX;
    private int N;
    private int[] pq;
    private int[] qp;
    private Key[] keys;
    public IndexMinPQ(int NMAX) {
        if(NMAX < 0)
            throw new IllegalArgumentException();
        this.NMAX = NMAX;
        keys = (Key[]) new Comparable[NMAX + 1];
        pq = new int[NMAX + 1];
        qp = new int[NMAX + 1];
        for(int i = 0; i <= NMAX; i++)
            qp[i] = -1;
    }
    public boolean isEmpty() {
        return N == 0;
    }
    public int size() {
        return N;
    }
    public boolean contains(int i) {
        if(i < 0 || i >= NMAX)
            throw new IndexOutOfBoundsException();
        return qp[i] != -1;
    }
    public void insert(int i, Key key) {
        if(i < 0 || i >= NMAX)
            throw new IndexOutOfBoundsException();
        if(contains(i))
            throw new IllegalArgumentException("index is already in the priority queue");
        N++;
        qp[i] = N;
        pq[N] = i;
        keys[i] = key;
        swim(N);
    }
    public int delMin() {
        if(N == 0)
            throw new NoSuchElementException("Priority queue underflow");
        int min = pq[1];
        exch(1, N--);
        sink(1);
        qp[min] = -1;
        keys[pq[N+1]] = null;
        pq[N+1] = -1;
        return min;
    }
    public void decreaseKey(int i, Key key) {
        if(i < 0 || i >= NMAX)
            throw new IndexOutOfBoundsException();
        if(!contains(i))
            throw new NoSuchElementException("index is not in the priority queue");
        if(keys[i].compareTo(key) <= 0)
            throw new IllegalArgumentException("calling decreaseKey() with given argument would not strictly decrease the key");
        keys[i] = key;
        sink(qp[i]);
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
        while(k > 1 && greater(k/2, k)) {
            exch(k, k/2);
            k = k/2;
        }
    }
    private void sink(int k) {
        while(2*k <= N) {
            int j = 2*k;
            if(j < N && greater(j, j+1))
                j++;
            if(!greater(k, j))
                break;
            exch(k, j);
            k = j;
        }
    }
    public Iterator<Integer> iterator() {
        return new HeapIterator();
    }
    private class HeapIterator implements Iterator<Integer> {
        private IndexMinPQ<Key> copy;
        public HeapIterator() {
            copy = new IndexMinPQ<Key>(pq.length - 1);
            for(int i = 1; i <= N; i++)
                copy.insert(pq[i], keys[pq[i]]);
        }
        public boolean hasNext() {
            return !copy.isEmpty();
        }
        public Integer next() {
            if(!hasNext())
                throw new NoSuchElementException();
            return copy.delMin();
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    public static void main(String[] args) {
        String[] strings = {"it", "was", "the", "best", "of", "times", "it", "was", "the", "worst"};
        IndexMinPQ<String> pq = new IndexMinPQ<String>(strings.length);
        for(int i = 0; i < strings.length; i++)
            pq.insert(i, strings[i]);
        while(!pq.isEmpty()) {
            int i = pq.delMin();
            System.out.println(i + " " + strings[i]);
        }
        System.out.println();
        for(int i = 0; i < strings.length; i++)
            pq.insert(i, strings[i]);
        for(int i : pq)
            System.out.println(i + " " + strings[i]);
        while(!pq.isEmpty())
            pq.delMin();
    }
}
