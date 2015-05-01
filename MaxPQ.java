import java.util.*;

public class MaxPQ<Key> implements Iterable<Key> {
    private Key[] pq;
    private int N;
    public MaxPQ(int initCap) {
        pq = (Key[]) new Object[initCap + 1];
        N = 0;
    }
    public MaxPQ() {
        this(1);
    }
    public boolean isEmpty() {
        return N == 0;
    }
    public int size() {
        return N;
    }
    private void resize(int capacity) {
        Key[] temp = (Key[]) new Object[capacity];
        for(int i = 1; i <= N; i++)
            temp[i] = pq[i];
        pq = temp;
    }
    public void insert(Key x) {
        if(N >= pq.length - 1)
            resize(2 * pq.length);
        pq[++N] = x;
        swim(N);
    }
    public Key delMax() {
        if(isEmpty())
            throw new NoSuchElementException("Priority queue underflow");
        Key max = pq[1];
        exch(1, N--);
        sink(1);
        pq[N+1] = null;
        if((N > 0) && (N == (pq.length - 1) / 4))
            resize(pq.length / 2);
        return max;
    }
    private void swim(int k) {
        while(k > 1 && less(k/2, k)) {
            exch(k, k/2);
            k = k/2;
        }
    }
    private void sink(int k) {
        while(2*k <= N) {
            int j = 2*k;
            if(j < N && less(j, j+1))
                j++;
            if(!less(k, j))
                break;
            exch(k, j);
            k = j;
        }
    }
    private boolean less(int i, int j) {
        return ((Comparable<Key>) pq[i]).compareTo(pq[j]) < 0;
    }
    private void exch(int i, int j) {
        Key swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
    }
    public Iterator<Key> iterator() {
        return new HeapIterator();
    }
    private class HeapIterator implements Iterator<Key> {
        private MaxPQ<Key> copy;
        public HeapIterator() {
            copy = new MaxPQ<Key>(size());
            for(int i = 1; i <= N; i++)
                copy.insert(pq[i]);
        }
        public boolean hasNext() {
            return !copy.isEmpty();
        }
        public Key next() {
            if(!hasNext())
                throw new NoSuchElementException();
            return copy.delMax();
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MaxPQ<String> pq = new MaxPQ<String>();
        while(scanner.hasNext()) {
            String item = scanner.next();
            if(!item.equals("-"))
                pq.insert(item);
/*
            else if(!pq.isEmpty())
                System.out.print(pq.delMax() + " ");
*/
        }
        //System.out.println("(" + pq.size() + " left on pq)");
        for(String s : pq)
            System.out.println(s);
    }
}
