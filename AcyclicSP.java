import java.util.*;

public class AcyclicSP {
    private double[] distTo;
    private DirectedEdge[] edgeTo;
    public AcyclicSP(EdgeWeightedDigraph G, int s) {
        distTo = new double[G.V()];
        edgeTo = new DirectedEdge[G.V()];
        for(int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        distTo[s] = 0.0;
        Topological top = new Topological(G);
        if(!top.hasOrder())
            throw new IllegalArgumentException("Digraph is not acyclic");
        for(int v : top.order())
            for(DirectedEdge e : G.adj(v))
                relax(e);
    }
    private void relax(DirectedEdge e) {
        int v = e.from(), w = e.to();
        if(distTo[w] > distTo[v] + e.weight()) {
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;
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
        int s = Integer.parseInt(args[0]);
        Scanner scanner = new Scanner(System.in);
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(scanner);
        AcyclicSP sp = new AcyclicSP(G, s);
        for(int v = 0; v < G.V(); v++) {
            if(sp.hasPathTo(v)) {
                System.out.printf("%d to %d (%.2f) ", s, v, sp.distTo(v));
                for(DirectedEdge e : sp.pathTo(v))
                    System.out.print(e + " ");
                System.out.println();
            } else {
                System.out.printf("%d to %d  no path\n", s, v);
            }
        }
    }
}
