import java.util.*;

public class EdgeWeightedDigraph {
    private final int V;
    private int E;
    private Bag<DirectedEdge>[] adj;
    public EdgeWeightedDigraph(int V) {
        if(V < 0)
            throw new IllegalArgumentException("number of vertices in a digraph must be nonnegative");
        this.V = V;
        this.E = 0;
        adj = (Bag<DirectedEdge>[]) new Bag[V];
        for(int v = 0; v < V; v++)
            adj[v] = new Bag<DirectedEdge>();
    }
    public EdgeWeightedDigraph(Scanner scanner) {
        this(scanner.nextInt());
        int E = scanner.nextInt();
        if(E < 0)
            throw new IllegalArgumentException("number of edges must be nonnegative");
        for(int i = 0; i < E; i++) {
            int v = scanner.nextInt();
            int w = scanner.nextInt();
            validateVertex(v);
            validateVertex(w);
            double weight = scanner.nextDouble();
            addEdge(new DirectedEdge(v, w, weight));
        }
    }
    public int V() {
        return V;
    }
    public int E() {
        return E;
    }
    private void validateVertex(int v) {
        if(v < 0 || v >= V)
            throw new IndexOutOfBoundsException("vertex " + v  + " is not between 0 and " + (V-1));
    }
    public void addEdge(DirectedEdge e) {
        int v = e.from();
        int w = e.to();
        validateVertex(v);
        validateVertex(w);
        adj[v].add(e);
        E++;
    }
    public Iterable<DirectedEdge> adj(int v) {
        validateVertex(v);
        return adj[v];
    }
    public Iterable<DirectedEdge> edges() {
        Bag<DirectedEdge> list = new Bag<DirectedEdge>();
        for(int v = 0; v < V; v++)
            for(DirectedEdge e : adj(v))
                list.add(e);
        return list;
    }
    public String toString() {
        String NEWLINE = System.getProperty("line.separator");
        StringBuilder s = new StringBuilder();
        s.append(V + " " + E + NEWLINE);
        for(int v = 0; v < V; v++) {
            s.append(v + ": ");
            for(DirectedEdge e : adj[v])
                s.append(e + " ");
            s.append(NEWLINE);
        }
        return s.toString();
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(scanner);
        System.out.println(G);
    }
}
