import java.util.*;

public class RedBlackBST<Key extends Comparable<Key>, Value> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;
    private Node root;
    private class Node {
        private Key key;
        private Value val;
        private Node left, right;
        private boolean color;
        private int N;
        public Node(Key key, Value val, boolean color, int N) {
            this.key = key;
            this.val = val;
            this.color = color;
            this.N = N;
        }
    }
    private boolean isRed(Node x) {
        if(x == null)
            return false;
        return (x.color == RED);
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
        while(x != null) {
            int cmp = key.compareTo(x.key);
            if(cmp < 0)
                x = x.left;
            else if(cmp > 0)
                x = x.right;
            else
                return x.val;
        }
        return null;
    }
    public boolean contains(Key key) {
        return get(key) != null;
    }
    public void put(Key key, Value val) {
        root = put(root, key, val);
        root.color = BLACK;
    }
    private Node put(Node x, Key key, Value val) {
        if(x == null)
            return new Node(key, val, RED, 1);
        int cmp = key.compareTo(x.key);
        if(cmp < 0)
            x.left = put(x.left, key, val);
        else if(cmp > 0)
            x.right = put(x.right, key, val);
        else
            x.val = val;
        x.N = size(x.left) + size(x.right) + 1;
        if(isRed(x.right) && !isRed(x.left))
            x = rotateLeft(x);
        if(isRed(x.left) && isRed(x.left.left))
            x = rotateRight(x);
        if(isRed(x.left) && isRed(x.right))
            flipColors(x);
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    public void deleteMin() {
        if(isEmpty())
            throw new NoSuchElementException("BST underflow");
        if(!isRed(root.left) && !isRed(root.right))
            root.color = RED;
        root = deleteMin(root);
        if(!isEmpty())
            root.color = BLACK;
    }
    private Node deleteMin(Node x) {
        if(x.left == null)
            return null;
        if(!isRed(x.left) && !isRed(x.left.left))
            x = moveRedLeft(x);
        x.left = deleteMin(x.left);
        return balance(x);
    }
    public void deleteMax() {
        if(isEmpty())
            throw new NoSuchElementException("BST underflow");
        if(!isRed(root.left) && !isRed(root.right))
            root.color = RED;
        root = deleteMax(root);
        if(!isEmpty())
            root.color = BLACK;
    }
    private Node deleteMax(Node x) {
        if(isRed(x.left))
            x = rotateRight(x);
        if(x.right == null)
            return null;
        if(!isRed(x.right) && !isRed(x.right.left))
            x = moveRedRight(x);
        x.right = deleteMax(x.right);
        return balance(x);
    }
    public void delete(Key key) {
        if(!contains(key)) {
            System.out.println("symbol table does not contain " + key);
            return;
        }
        if(!isRed(root.left) && !isRed(root.right))
            root.color = RED;
        root = delete(root, key);
        if(!isEmpty())
            root.color = BLACK;
    }
    private Node delete(Node x, Key key) {
        if(key.compareTo(x.key) < 0) {
            if(!isRed(x.left) && !isRed(x.left.left))
                x = moveRedLeft(x);
            x.left = delete(x.left, key);
        }
        else {
            if(isRed(x.left))
                x = rotateRight(x);
            if(key.compareTo(x.key) == 0 && (x.right == null))
                return null;
            if(!isRed(x.right) && !isRed(x.right.left))
                x = moveRedRight(x);
            if(key.compareTo(x.key) == 0) {
                Node n = min(x.right);
                x.key = n.key;
                x.val = n.val;
                x.right = deleteMin(x.right);
            }
            else
                x.right = delete(x.right, key);
        }
        return balance(x);
    }

    private Node rotateRight(Node x) {
        Node n = x.left;
        x.left = n.right;
        n.right = x;
        n.color = n.right.color;
        n.right.color = RED;
        n.N = x.N;
        x.N = size(x.left) + size(x.right) + 1;
        return n;
    }
    private Node rotateLeft(Node x) {
        Node n = x.right;
        x.right = n.left;
        n.left = x;
        n.color = n.left.color;
        n.left.color = RED;
        n.N = x.N;
        x.N = size(x.left) + size(x.right) + 1;
        return n;
    }
    private void flipColors(Node x) {
        x.color = !x.color;
        x.left.color = !x.left.color;
        x.right.color = !x.right.color;
    }
    private Node moveRedLeft(Node x) {
        flipColors(x);
        if(isRed(x.right.left)) {
            x.right = rotateRight(x.right);
            x = rotateLeft(x);
            flipColors(x);
        }
        return x;
    }
    private Node moveRedRight(Node x) {
        flipColors(x);
        if(isRed(x.left.left)) {
            x = rotateRight(x);
            flipColors(x);
        }
        return x;
    }
    private Node balance(Node x) {
        if(isRed(x.right))
            x = rotateLeft(x);
        if(isRed(x.left) && isRed(x.left.left))
            x = rotateRight(x);
        if(isRed(x.left) && isRed(x.right))
            flipColors(x);
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
        RedBlackBST<String, Integer> st = new RedBlackBST<String, Integer>();
        for(int i = 0; scanner.hasNext(); i++) {
            String key = scanner.next();
            st.put(key, i);
        }
        for(String s : st.keys())
            System.out.println(s + " " + st.get(s));
    }
}
