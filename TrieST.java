import java.util.*;

public class TrieST<Value> {
    private static final int R = 256;
    private Node root;
    private int N;
    private static class Node {
        private Object val;
        private Node[] next = new Node[R];
    }
    public TrieST() {
    }
    public Value get(String key) {
        Node x = get(root, key, 0);
        if(x == null)
            return null;
        return (Value) x.val;
    }
    private Node get(Node x, String key, int d) {
        if(x == null)
            return null;
        if(d == key.length())
            return x;
        char c = key.charAt(d);
        return get(x.next[c], key, d+1);
    }
    public boolean contains(String key) {
        return get(key) != null;
    }
    public void put(String key, Value val) {
        if(val == null)
            delete(key);
        else
            root = put(root, key, val, 0);
    }
    private Node put(Node x, String key, Value val, int d) {
        if(x == null)
            x = new Node();
        if(d == key.length()) {
            if(x.val == null)
                N++;
            x.val = val;
            return x;
        }
        char c = key.charAt(d);
        x.next[c] = put(x.next[c], key, val, d+1);
        return x;
    }
    public int size() {
        return N;
    }
    public boolean isEmpty() {
        return size() == 0;
    }
    public Iterable<String> keys() {
        return keysWithPrefix("");
    }
    public Iterable<String> keysWithPrefix(String prefix) {
        Queue<String> results = new Queue<String>();
        Node x = get(root, prefix, 0);
        collect(x, new StringBuilder(prefix), results);
        return results;
    }
    private void collect(Node x, StringBuilder prefix, Queue<String> results) {
        if(x == null)
            return;
        if(x.val != null)
            results.add(prefix.toString());
        for(char c = 0; c < R; c++) {
            prefix.append(c);
            collect(x.next[c], prefix, results);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }
    public Iterable<String> keysThatMatch(String pattern) {
        Queue<String> results = new Queue<String>();
        collect(root, new StringBuilder(), pattern, results);
        return results;
    }
    private void collect(Node x, StringBuilder prefix, String pattern, Queue<String> results) {
        if(x == null)
            return;
        int d = prefix.length();
        if(d == pattern.length() && x.val != null)
            results.add(prefix.toString());
        if(d == pattern.length())
            return;
        char c = pattern.charAt(d);
        if(c == '.') {
            for(char ch = 0; ch < R; ch++) {
                prefix.append(ch);
                collect(x.next[ch], prefix, pattern, results);
                prefix.deleteCharAt(prefix.length() - 1);
            }
        } else {
            prefix.append(c);
            collect(x.next[c], prefix, pattern, results);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }
    public String longestPrefixOf(String query) {
        int length = longestPrefixOf(root, query, 0, -1);
        if(length == -1)
            return null;
        else
            return query.substring(0, length);
    }
    private int longestPrefixOf(Node x, String query, int d, int length) {
        if(x == null)
            return length;
        if(x.val != null)
            length = d;
        if(d == query.length())
            return length;
        char c = query.charAt(d);
        return longestPrefixOf(x.next[c], query, d+1, length);
    }
    public void delete(String key) {
        root = delete(root, key, 0);
    }
    private Node delete(Node x, String key, int d) {
        if(x == null)
            return null;
        if(d == key.length()) {
            if(x.val != null)
                N--;
            x.val = null;
        } else {
            char c = key.charAt(d);
            x.next[c] = delete(x.next[c], key, d+1);
        }
        if(x.val != null)
            return x;
        for(int c = 0; c < R; c++)
            if(x.next[c] != null)
                return x;
        return null;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TrieST<Integer> st = new TrieST<Integer>();
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

        System.out.println("longestPrefixOf(\"quicksort\"):");
        System.out.println(st.longestPrefixOf("quicksort"));
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
