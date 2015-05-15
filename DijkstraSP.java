import java.util.*;

public class DijkstraSP {
    private double[] distTo;
    private DirectedEdge[] edgeTo;
    private IndexMinPQ<Double> pq;
    public DijkstraSP(EdgeWeightedDigraph G, int s) {
        for(DirectedEdge e : G.edges()) {
            if(e.weight() < 0)
                throw new IllegalArgumentException("edge " + e + " has negative weight");
        }
        distTo = new double[G.V()];
        edgeTo = new DirectedEdge[G.V()];
        for(int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        distTo[s] = 0.0;
        pq = new IndexMinPQ<Double>(G.V());
        pq.insert(s, distTo[s]);
        while(!pq.isEmpty()) {
            int v = pq.delMin();
            for(DirectedEdge e : G.adj(v))
                relax(e);
        }
    }
    private void relax(DirectedEdge e) {
        int v = e.from(), w = e.to();
        if(distTo[w] > distTo[v] + e.weight()) {
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;
            if(pq.contains(w))
                pq.decreaseKey(w, distTo[w]);
            else
                pq.insert(w, distTo[w]);
        }
    }
    public double distTo(int v) {
        return distTo[v];
    }
    public boolean hasPathTo(int v) {
        return distTo[v] < Double.POSITIVE_INFINITY;
    }
    public Iterable<DirectedEdge> pathTo(int v) {
        if(!hasPathTo(v))
            return null;
        Stack<DirectedEdge> path = new Stack<DirectedEdge>();
        for(DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()])
            path.push(e);
        return path;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(scanner);
        int s = Integer.parseInt(args[0]);
        DijkstraSP sp = new DijkstraSP(G, s);
        for(int t = 0; t < G.V(); t++) {
            if(sp.hasPathTo(t)) {
                System.out.printf("%d to %d (%.2f) ", s, t, sp.distTo(t));
                if(sp.hasPathTo(t)) {
                    for(DirectedEdge e : sp.pathTo(t))
                        System.out.print(e + " ");
                }
                System.out.println();
            } else {
                System.out.printf("%d to %d  no path\n", s, t);
            }
        }
    }
}
