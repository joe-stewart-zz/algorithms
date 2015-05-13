import java.util.*;

public class EdgeWeightedGraph {
    private final int V;
    private int E;
    private Bag<Edge>[] adj;
    public EdgeWeightedGraph(int V) {
        if(V < 0)
            throw new IllegalArgumentException("Number of vertices must be nonnegative");
        this.V = V;
        this.E = 0;
        adj = (Bag<Edge>[]) new Bag[V];
        for(int v = 0; v < V; v++)
            adj[v] = new Bag<Edge>();
    }
    public EdgeWeightedGraph(Scanner scanner) {
        this(scanner.nextInt());
        int E = scanner.nextInt();
        if(E < 0)
            throw new IllegalArgumentException("Number of edges must be nonnegative");
        for(int i = 0; i < E; i++) {
            int v = scanner.nextInt();
            int w = scanner.nextInt();
            double weight = scanner.nextDouble();
            Edge e = new Edge(v, w, weight);
            addEdge(e);
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
            throw new IndexOutOfBoundsException("vertex " + V + " is not betwen 0 and " + (V-1));
    }
    public void addEdge(Edge e) {
        int v = e.either();
        int w = e.other(v);
        validateVertex(v);
        validateVertex(w);
        adj[v].add(e);
        adj[w].add(e);
        E++;
    }
    public Iterable<Edge> adj(int v) {
        validateVertex(v);
        return adj[v];
    }
    public int degree(int v) {
        validateVertex(v);
        return adj[v].size();
    }
    public Iterable<Edge> edges() {
        Bag<Edge> list = new Bag<Edge>();
        for(int v = 0; v < V; v++) {
            int selfLoops = 0;
            for(Edge e : adj(v)) {
                if(e.other(v) > v) {
                    list.add(e);
                } else if(e.other(v) == v) {
                    if(selfLoops % 2 == 0)
                        list.add(e);
                    selfLoops++;
                }
            }
        }
        return list;
    }
    public String toString() {
        String NEWLINE = System.getProperty("line.separator");
        StringBuilder s = new StringBuilder();
        s.append(V + " " + E + NEWLINE);
        for(int v = 0; v < V; v++) {
            s.append(v + ": ");
            for(Edge e : adj[v])
                s.append(e + " ");
            s.append(NEWLINE);
        }
        return s.toString();
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EdgeWeightedGraph G = new EdgeWeightedGraph(scanner);
        System.out.println(G);
    }
}
