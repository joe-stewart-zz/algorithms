import java.util.*;

public class EdgeWeightedDirectedCycle {
    private boolean[] marked;
    private DirectedEdge[] edgeTo;
    private boolean[] onStack;
    private Stack<DirectedEdge> cycle;
    public EdgeWeightedDirectedCycle(EdgeWeightedDigraph G) {
        marked = new boolean[G.V()];
        onStack = new boolean[G.V()];
        edgeTo = new DirectedEdge[G.V()];
        for(int v = 0; v < G.V(); v++)
            if(!marked[v])
                dfs(G, v);
    }
    private void dfs(EdgeWeightedDigraph G, int v) {
        onStack[v] = true;
        marked[v] = true;
        for(DirectedEdge e : G.adj(v)) {
            int w = e.to();
            if(cycle != null)
                return;
            else if(!marked[w]) {
                edgeTo[w] = e;
                dfs(G, w);
            }
            else if(onStack[w]) {
                cycle = new Stack<DirectedEdge>();
                while(e.from() != w) {
                    cycle.push(e);
                    e = edgeTo[e.from()];
                }
                cycle.push(e);
            }
        }
        onStack[v] = false;
    }
    public boolean hasCycle() {
        return cycle != null;
    }
    public Iterable<DirectedEdge> cycle() {
        return cycle;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(scanner);
        EdgeWeightedDirectedCycle finder = new EdgeWeightedDirectedCycle(G);
        if(finder.hasCycle()) {
            System.out.print("Cycle: ");
            for(DirectedEdge e : finder.cycle())
                System.out.print(e + " ");
            System.out.println();
        } else {
            System.out.println("No cycle");
        }
    }
}
