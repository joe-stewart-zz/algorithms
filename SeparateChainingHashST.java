import java.util.*;

public class SeparateChainingHashST<Key, Value> {
    private static final int INIT_CAP = 4;

    private int N;
    private int M;
    private SequentialSearchST<Key, Value>[] st;

    public SeparateChainingHashST() {
        this(INIT_CAP);
    }
    public SeparateChainingHashST(int M) {
        this.M = M;
        st = (SequentialSearchST<Key, Value>[]) new SequentialSearchST[M];
        for(int i = 0; i < M; i++)
            st[i] = new SequentialSearchST<Key, Value>();
    }
    private void resize(int chains) {
        SeparateChainingHashST<Key, Value> temp = new SeparateChainingHashST<>(chains);
        for(int i = 0; i < M; i++) {
            for(Key key : st[i].keys()) {
                temp.put(key, st[i].get(key));
            }
        }
        this.M = temp.M;
        this.N = temp.N;
        this.st = temp.st;
    }
    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }
    public int size() {
        return N;
    }
    public boolean isEmpty() {
        return size() == 0;
    }
    public boolean contains(Key key) {
        return get(key) != null;
    }
    public Value get(Key key) {
        int i = hash(key);
        return st[i].get(key);
    }
    public void put(Key key, Value val) {
        if(val == null) {
            delete(key);
            return;
        }
        if(N >= 10*M)
            resize(2*M);
        int i = hash(key);
        if(!st[i].contains(key))
            N++;
        st[i].put(key, val);
    }
    public void delete(Key key) {
        int i = hash(key);
        if(st[i].contains(key))
            N--;
        st[i].delete(key);
        if(M > INIT_CAP && N <= 2*M)
            resize(M/2);
    }
    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<>();
        for(int i = 0; i < M; i++) {
            for(Key key : st[i].keys())
                queue.add(key);
        }
        return queue;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SeparateChainingHashST<String, Integer> st = new SeparateChainingHashST<String, Integer>();
        for(int i = 0; scanner.hasNext(); i++) {
            String s = scanner.next();
            st.put(s, i);
        }
        for(String s : st.keys())
            System.out.println(s + " " + st.get(s));
    }
}
