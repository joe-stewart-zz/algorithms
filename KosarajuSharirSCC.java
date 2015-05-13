import java.util.*;

public class KosarajuSharirSCC {
    private boolean[] marked;
    private int[] id;
    private int count;
    public KosarajuSharirSCC(Digraph G) {
        DepthFirstOrder dfs = new DepthFirstOrder(G.reverse());
        marked = new boolean[G.V()];
        id = new int[G.V()];
        for(int v : dfs.reversePost()) {
            if(!marked[v]) {
                dfs(G, v);
                count++;
            }
        }
    }
    private void dfs(Digraph G, int v) {
        marked[v] = true;
        id[v] = count;
        for(int w : G.adj(v))
            if(!marked[w])
                dfs(G, w);
    }
    public int count() {
        return count;
    }
    public boolean stronglyConnected(int v, int w) {
        return id[v] == id[w];
    }
    public int id(int v) {
        return id[v];
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Digraph G = new Digraph(scanner);
        KosarajuSharirSCC scc = new KosarajuSharirSCC(G);
        int M = scc.count();
        System.out.println(M + " components");
        Queue<Integer>[] components = (Queue<Integer>[]) new Queue[M];
        for(int i = 0; i < M; i++)
            components[i] = new Queue<Integer>();
        for(int v = 0; v < G.V(); v++)
            components[scc.id(v)].add(v);
        for(int i = 0; i < M; i++) {
            for(int v : components[i])
                System.out.print(v + " ");
            System.out.println();
        }
    }
}
