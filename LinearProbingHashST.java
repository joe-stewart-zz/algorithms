import java.util.*;

public class LinearProbingHashST<Key, Value> {
    private static final int INIT_CAP = 4;
    private int N;
    private int M;
    private Key[] keys;
    private Value[] vals;
    public LinearProbingHashST() {
        this(INIT_CAP);
    }
    public LinearProbingHashST(int cap) {
        M = cap;
        keys = (Key[]) new Object[M];
        vals = (Value[]) new Object[M];
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
    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }
    private void resize(int cap) {
        LinearProbingHashST<Key, Value> temp = new LinearProbingHashST<Key, Value>(cap);
        for(int i = 0; i < M; i++) {
            if(keys[i] != null) {
                temp.put(keys[i], vals[i]);
            }
        }
        keys = temp.keys;
        vals = temp.vals;
        M = temp.M;
    }
    public void put(Key key, Value val) {
        if(val == null) {
            delete(key);
            return;
        }
        if(N >= M/2)
            resize(2*M);
        int i;
        for(i = hash(key); keys[i] != null; i = (i+1) % M) {
            if(keys[i].equals(key)) {
                vals[i] = val;
                return;
            }
        }
        keys[i] = key;
        vals[i] = val;
        N++;
    }
    public Value get(Key key) {
        for(int i = hash(key); keys[i] != null; i = (i+1) % M)
            if(keys[i].equals(key))
                return vals[i];
        return null;
    }
    public void delete(Key key) {
        if(!contains(key))
            return;
        int i = hash(key);
        while(!key.equals(keys[i]))
            i = (i+1) % M;
        keys[i] = null;
        vals[i] = null;
        i = (i+1) % M;
        while(keys[i] != null) {
            Key keyToRehash = keys[i];
            Value valToRehash = vals[i];
            keys[i] = null;
            vals[i] = null;
            N--;
            put(keyToRehash, valToRehash);
            i = (i+1) % M;
        }
        N--;
        if(N > 0 && N <= M/8)
            resize(M/2);
    }
    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<Key>();
        for(int i = 0; i < M; i++)
            if(keys[i] != null)
                queue.add(keys[i]);
        return queue;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LinearProbingHashST<String, Integer> st = new LinearProbingHashST<>();
        for(int i = 0; scanner.hasNext(); i++) {
            String s = scanner.next();
            st.put(s, i);
        }
        for(String s : st.keys())
            System.out.println(s + " " + st.get(s));
    }
}
