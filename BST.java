import java.util.*;

public class BST<Key extends Comparable<Key>, Value> {
    private Node root;
    private class Node {
        private Key key;
        private Value val;
        private Node left, right;
        private int N;
        public Node(Key key, Value val, int N) {
            this.key = key;
            this.val = val;
            this.N = N;
        }
    }
    public int size() {
        return size(root);
    }
    private int size(Node x) {
        if(x == null)
            return 0;
        else
            return x.N;
    }
    public boolean isEmpty() {
        return size() == 0;
    }
    public Value get(Key key) {
        return get(root, key);
    }
    private Value get(Node x, Key key) {
        if(x == null)
            return null;
        int cmp = key.compareTo(x.key);
        if(cmp < 0)
            return get(x.left, key);
        else if(cmp > 0)
            return get(x.right, key);
        else
            return x.val;
    }
    public void put(Key key, Value val) {
        root = put(root, key, val);
    }
    private Node put(Node x, Key key, Value val) {
        if(x == null)
            return new Node(key, val, 1);
        int cmp = key.compareTo(x.key);
        if(cmp < 0)
            x.left = put(x.left, key, val);
        else if(cmp > 0)
            x.right = put(x.right, key, val);
        else
            x.val = val;
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    public void deleteMin() {
        if(isEmpty())
            throw new NoSuchElementException("Symbol table underflow");
        root = deleteMin(root);
    }
    private Node deleteMin(Node x) {
        if(x.left == null)
            return x.right;
        x.left = deleteMin(x.left);
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }
    public void deleteMax() {
        if(isEmpty())
            throw new NoSuchElementException("Symbol table underflow");
        root = deleteMax(root);
    }
    private Node deleteMax(Node x) {
        if(x.right == null)
            return x.left;
        x.right = deleteMax(x.right);
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }
    public void delete(Key key) {
        root = delete(root, key);
    }
    private Node delete(Node x, Key key) {
        if(x == null)
            return null;
        int cmp = key.compareTo(x.key);
        if(cmp < 0)
            x.left = delete(x.left, key);
        else if(cmp > 0)
            x.right = delete(x.right, key);
        else {
            if(x.right == null)
                return x.left;
            if(x.left == null)
                return x.right;
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    public Key select(int k) {
        if(k < 0 || k >= size())
            return null;
        Node x = select(root, k);
        return x.key;
    }
    private Node select(Node x, int k) {
        if(x == null)
            return null;
        int t = size(x.left);
        if(t > k)
            return select(x.left, k);
        else if(t < k)
            return select(x.right, k-t-1);
        else
            return x;
    }
    public int rank(Key key) {
        return rank(key, root);
    }
    private int rank(Key key, Node x) {
        if(x == null)
            return 0;
        int cmp = key.compareTo(x.key);
        if(cmp < 0)
            return rank(key, x.left);
        else if(cmp > 0)
            return 1 + size(x.left) + rank(key, x.right);
        else
            return size(x.left);
    }
    public Iterable<Key> keys() {
        return keys(min(), max());
    }
    public Iterable<Key> keys(Key lo, Key hi) {
        Queue<Key> queue = new Queue<Key>();
        keys(root, queue, lo, hi);
        return queue;
    }
    private void keys(Node x, Queue<Key> queue, Key lo, Key hi) {
        if(x == null)
            return;
        int cmplo = lo.compareTo(x.key);
        int cmphi = hi.compareTo(x.key);
        if(cmplo < 0)
            keys(x.left, queue, lo, hi);
        if(cmplo <= 0 && cmphi >= 0)
            queue.add(x.key);
        if(cmphi > 0)
            keys(x.right, queue, lo, hi);
    }
    public Key min() {
        return min(root).key;
    }
    private Node min(Node x) {
        if(x.left == null)
            return x;
        return min(x.left);
    }
    public Key max() {
        if(isEmpty())
            return null;
        return max(root).key;
    }
    private Node max(Node x) {
        if(x.right == null)
            return x;
        else
            return max(x.right);
    }
    public Key floor(Key key) {
        Node x = floor(root, key);
        if(x == null)
            return null;
        else
            return x.key;
    }
    private Node floor(Node x, Key key) {
        if(x == null)
            return null;
        int cmp = key.compareTo(x.key);
        if(cmp == 0)
            return x;
        if(cmp < 0)
            return floor(x.left, key);
        Node t = floor(x.right, key);
        if(t != null)
            return t;
        else
            return x;
    }
    public Key ceiling(Key key) {
        Node x = ceiling(root, key);
        if(x == null)
            return null;
        else
            return x.key;
    }
    private Node ceiling(Node x, Key key) {
        if(x == null)
            return null;
        int cmp = key.compareTo(x.key);
        if(cmp == 0)
            return x;
        if(cmp < 0) {
            Node t = ceiling(x.left, key);
            if(t != null)
                return t;
            else
                return x;
        }
        return ceiling(x.right, key);
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BST<String, Integer> st = new BST<String, Integer>();
        for(int i = 0; scanner.hasNext(); i++) {
            String key = scanner.next();
            st.put(key, i);
        }
        for(String s : st.keys())
            System.out.println(s + " " + st.get(s));
    }
}
