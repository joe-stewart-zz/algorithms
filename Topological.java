public class Topological {
    private Iterable<Integer> order;
    public Topological(Digraph G) {
        DirectedCycle finder = new DirectedCycle(G);
        if(!finder.hasCycle()) {
            DepthFirstOrder dfs = new DepthFirstOrder(G);
            order = dfs.reversePost();
        }
    }
    public Topological(EdgeWeightedDigraph G) {
        EdgeWeightedDirectedCycle finder = new EdgeWeightedDirectedCycle(G);
        if(!finder.hasCycle()) {
            DepthFirstOrder dfs = new DepthFirstOrder(G);
            order = dfs.reversePost();
        }
    }
    public Iterable<Integer> order() {
        return order;
    }
    public boolean hasOrder() {
        return order != null;
    }
    public static void main(String[] args) {
        SymbolDigraph sg = new SymbolDigraph(args[0], args[1]);
        Topological top = new Topological(sg.G());
        for(int v : top.order())
            System.out.println(sg.name(v));
    }
}
