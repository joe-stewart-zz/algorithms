import java.util.*;

public class DepthFirstPaths {
    private boolean[] marked;
    private int[] edgeTo;
    private final int s;
    public DepthFirstPaths(Graph G, int s) {
        this.s = s;
        edgeTo = new int[G.V()];
        marked = new boolean[G.V()];
        dfs(G, s);
    }
    private void dfs(Graph G, int v) {
        marked[v] = true;
        for(int w : G.adj(v)) {
            if(!marked[w]) {
                edgeTo[w] = v;
                dfs(G, w);
            }
        }
    }
    public boolean hasPathTo(int v) {
        return marked[v];
    }
    public Iterable<Integer> pathTo(int v) {
        if(!hasPathTo(v))
            return null;
        Stack<Integer> path = new Stack<>();
        for(int x = v; x != s; x = edgeTo[x])
            path.push(x);
        path.push(s);
        return path;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Graph G = new Graph(scanner);
        int s = Integer.parseInt(args[0]);
        DepthFirstPaths dfs = new DepthFirstPaths(G, s);
        for(int v = 0; v < G.V(); v++) {
            if(dfs.hasPathTo(v)) {
                System.out.printf("%d to %d:  ", s, v);
                for(int x : dfs.pathTo(v)) {
                    if(x == s)
                        System.out.print(x);
                    else
                        System.out.print("-" + x);
                }
                System.out.println();
            } else {
                System.out.printf("%d to %d:  not connected\n", s, v);
            }
        }
    }
}
