import java.util.*;

public class BellmanFordSP {
    private double[] distTo;
    private DirectedEdge[] edgeTo;
    private boolean[] onQueue;
    private Queue<Integer> queue;
    private int cost;
    private Iterable<DirectedEdge> cycle;
    public BellmanFordSP(EdgeWeightedDigraph G, int s) {
        distTo = new double[G.V()];
        edgeTo = new DirectedEdge[G.V()];
        onQueue = new boolean[G.V()];
        for(int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        distTo[s] = 0.0;
        queue = new Queue<Integer>();
        queue.add(s);
        while(!queue.isEmpty() && !hasNegativeCycle()) {
            int v = queue.remove();
            onQueue[v] = false;
            relax(G, v);
        }
    }
    private void relax(EdgeWeightedDigraph G, int v) {
        for(DirectedEdge e : G.adj(v)) {
            int w = e.to();
            if(distTo[w] > distTo[v] + e.weight()) {
                distTo[w] = distTo[v] + e.weight();
                edgeTo[w] = e;
                if(!onQueue[w]) {
                    queue.add(w);
                    onQueue[w] = true;
                }
            }
            if(cost++ % G.V() == 0) {
                findNegativeCycle();
                if(hasNegativeCycle())
                    return;
            }
        }
    }
    public boolean hasNegativeCycle() {
        return cycle != null;
    }
    public Iterable<DirectedEdge> negativeCycle() {
        return cycle;
    }
    private void findNegativeCycle() {
        int V = edgeTo.length;
        EdgeWeightedDigraph spt = new EdgeWeightedDigraph(V);
        for(int v = 0; v < V; v++)
            if(edgeTo[v] != null)
                spt.addEdge(edgeTo[v]);
        EdgeWeightedDirectedCycle finder = new EdgeWeightedDirectedCycle(spt);
        cycle = finder.cycle();
    }
    public double distTo(int v) {
        if(hasNegativeCycle())
            throw new UnsupportedOperationException("Negative cost cycle exists");
        return distTo[v];
    }
    public boolean hasPathTo(int v) {
        return distTo[v] < Double.POSITIVE_INFINITY;
    }
    public Iterable<DirectedEdge> pathTo(int v) {
        if(hasNegativeCycle())
            throw new UnsupportedOperationException("Negative cost cycle exists");
        if(!hasPathTo(v))
            return null;
        Stack<DirectedEdge> path = new Stack<DirectedEdge>();
        for(DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()])
            path.push(e);
        return path;
    }
    public static void main(String[] args) {
        int s = Integer.parseInt(args[0]);
        Scanner scanner = new Scanner(System.in);
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(scanner);
        BellmanFordSP sp = new BellmanFordSP(G, s);
        if(sp.hasNegativeCycle())
            for(DirectedEdge e : sp.negativeCycle())
                System.out.println(e);
        else {
            for(int v = 0; v < G.V(); v++) {
                if(sp.hasPathTo(v)) {
                    System.out.printf("%d to %d (%5.2f) ", s, v, sp.distTo(v));
                    for(DirectedEdge e : sp.pathTo(v))
                        System.out.print(e + " ");
                    System.out.println();
                } else {
                    System.out.printf("%d to %d  no path\n", s, v);
                }
            }
        }
    }
}
