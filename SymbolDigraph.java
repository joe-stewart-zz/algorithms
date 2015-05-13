import java.util.*;
import java.io.*;

public class SymbolDigraph {
    private TreeMap<String, Integer> st;
    private String[] keys;
    private Digraph G;
    Scanner scanner;
    public SymbolDigraph(String filename, String delimiter) {
        try {
            scanner = new Scanner(new File(filename));
        } catch(FileNotFoundException fne) {
            fne.printStackTrace();
        }
        st = new TreeMap<String, Integer>();
        while(scanner.hasNextLine()) {
            String[] a = scanner.nextLine().split(delimiter);
            for(int i = 0; i < a.length; i++)
                if(!st.containsKey(a[i]))
                    st.put(a[i], st.size());
        }
        keys = new String[st.size()];
        for(String name : st.keySet())
            keys[st.get(name)] = name;
        try {
            scanner = new Scanner(new File(filename));
        } catch(FileNotFoundException fne) {
            fne.printStackTrace();
        }
        G = new Digraph(st.size());
        while(scanner.hasNextLine()) {
            String[] a = scanner.nextLine().split(delimiter);
            int v = st.get(a[0]);
            for(int i = 1; i < a.length; i++) {
                int w = st.get(a[i]);
                G.addEdge(v, w);
            }
        }
    }
    public boolean contains(String s) {
        return st.containsKey(s);
    }
    public int index(String s) {
        return st.get(s);
    }
    public String name(int v) {
        return keys[v];
    }
    public Digraph G() {
        return G;
    }
    public static void main(String[] args) {
        SymbolDigraph sg = new SymbolDigraph(args[0], args[1]);
        Digraph G = sg.G();
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()) {
            String s = scanner.next();
            for(int v : G.adj(sg.index(s)))
                System.out.println("   " + sg.name(v));
        }
    }
}
