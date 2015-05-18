import java.util.*;

public class TST<Value> {
    private Node root;
    private int N;
    private static class Node<Value> {
        private char c;
        private Node<Value> left, mid, right;
        private Value val;
    }
    public TST() {
    }
    public Value get(String key) {
        if(key == null)
            throw new NullPointerException();
        if(key.length() == 0)
            throw new IllegalArgumentException("key length must be >= 1");
        Node<Value> x = get(root, key, 0);
        if(x == null)
            return null;
        return x.val;
    }
    private Node<Value> get(Node<Value> x, String key, int d) {
        if(key == null)
            throw new NullPointerException();
        if(key.length() == 0)
            throw new IllegalArgumentException("key length must be >= 1");
        if(x == null)
            return null;
        char c = key.charAt(d);
        if(c < x.c)
            return get(x.left, key, d);
        else if(c > x.c)
            return get(x.right, key, d);
        else if(d < key.length() - 1)
            return get(x.mid, key, d+1);
        else
            return x;
    }
    public boolean contains(String key) {
        return get(key) != null;
    }
    public void put(String key, Value val) {
        if(!contains(key))
            N++;
        root = put(root, key, val, 0);
    }
    private Node<Value> put(Node<Value> x, String key, Value val, int d) {
        char c = key.charAt(d);
        if(x == null) {
            x = new Node<Value>();
            x.c = c;
        }
        if(c < x.c)
            x.left = put(x.left, key, val, d);
        else if(c > x.c)
            x.right = put(x.right, key, val, d);
        else if(d < key.length() - 1)
            x.mid = put(x.mid, key, val, d+1);
        else
            x.val = val;
        return x;
    }
    public int size() {
        return N;
    }
    public boolean isEmpty() {
        return size() == 0;
    }
    public Iterable<String> keys() {
        Queue<String> queue = new Queue<String>();
        collect(root, new StringBuilder(), queue);
        return queue;
    }
    public Iterable<String> keysWithPrefix(String prefix) {
        Queue<String> results = new Queue<String>();
        Node<Value> x = get(root, prefix, 0);
        if(x == null)
            return results;
        if(x.val != null)
            results.add(prefix);
        collect(x.mid, new StringBuilder(prefix), results);
        return results;
    }
    private void collect(Node<Value> x, StringBuilder prefix, Queue<String> results) {
        if(x == null)
            return;
        collect(x.left, prefix, results);
        if(x.val != null)
            results.add(prefix.toString() + x.c);
        collect(x.mid, prefix.append(x.c), results);
        prefix.deleteCharAt(prefix.length() - 1);
        collect(x.right, prefix, results);
    }
    public Iterable<String> keysThatMatch(String pattern) {
        Queue<String> results = new Queue<String>();
        collect(root, new StringBuilder(), 0, pattern, results);
        return results;
    }
    private void collect(Node<Value> x, StringBuilder prefix, int i, String pattern, Queue<String> results) {
        if(x == null)
            return;
        char c = pattern.charAt(i);
        if(c == '.' || c < x.c)
            collect(x.left, prefix, i, pattern, results);
        if(c == '.' || c == x.c) {
            if(i == pattern.length() - 1 && x.val != null)
                results.add(prefix.toString() + x.c);
            if(i < pattern.length() - 1) {
                collect(x.mid, prefix.append(x.c), i+1, pattern, results);
                prefix.deleteCharAt(prefix.length() - 1);
            }
        }
        if(c == '.' || c > x.c)
            collect(x.right, prefix, i, pattern, results);
    }
    public String longestPrefixOf(String query) {
        if(query == null || query.length() == 0)
            return null;
        int length = 0;
        Node<Value> x = root;
        int i = 0;
        while(x != null && i < query.length()) {
            char c = query.charAt(i);
            if(c < x.c)
                x = x.left;
            else if(c > x.c)
                x = x.right;
            else {
                i++;
                if(x.val != null)
                    length = i;
                x = x.mid;
            }
        }
        return query.substring(0, length);
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TST<Integer> st = new TST<Integer>();
        for(int i = 0; scanner.hasNext(); i++) {
            String key = scanner.next();
            st.put(key, i);
        }
        if(st.size() < 100) {
            System.out.println("keys(\"\"):");
            for(String key : st.keys())
                System.out.println(key + " " + st.get(key));
            System.out.println();
        }
        System.out.println("longestPrefixOf(\"shellsort\"):");
        System.out.println(st.longestPrefixOf("shellsort"));
        System.out.println();

        System.out.println("keysWithPrefix(\"shor\"):");
        for(String s : st.keysWithPrefix("shor"))
            System.out.println(s);
        System.out.println();

        System.out.println("keysThatMatch(\".he.l.\"):");
        for(String s : st.keysThatMatch(".he.l."))
            System.out.println(s);
    }
}
