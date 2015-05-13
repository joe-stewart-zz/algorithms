import java.util.*;

public class KruskalMST {
    private double weight;
    private Queue<Edge> mst = new Queue<Edge>();
    public KruskalMST(EdgeWeightedGraph G) {
        MinPQ<Edge> pq = new MinPQ<Edge>();
        for(Edge e : G.edges())
            pq.insert(e);
        UF uf = new UF(G.V());
        while(!pq.isEmpty() && mst.size() < G.V() - 1) {
            Edge e = pq.delMin();
            int v = e.either();
            int w = e.other(v);
            if(!uf.connected(v, w)) {
                uf.union(v, w);
                mst.add(e);
                weight += e.weight();
            }
        }
    }
    public Iterable<Edge> edges() {
        return mst;
    }
    public double weight() {
        return weight;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EdgeWeightedGraph G = new EdgeWeightedGraph(scanner);
        KruskalMST mst = new KruskalMST(G);
        for(Edge e : mst.edges())
            System.out.println(e);
        System.out.printf("%.5f\n", mst.weight());
    }
}
