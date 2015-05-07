import java.util.*;

public class BreadthFirstPaths {
    private boolean[] marked;
    private int[] edgeTo;
    private final int s;
    public BreadthFirstPaths(Graph G, int s) {
        this.s = s;
        edgeTo = new int[G.V()];
        marked = new boolean[G.V()];
        bfs(G, s);
    }
    private void bfs(Graph G, int s) {
        Queue<Integer> queue = new Queue<Integer>();
        marked[s] = true;
        queue.add(s);
        while(!queue.isEmpty()) {
            int v = queue.remove();
            for(int w : G.adj(v))
                if(!marked[w]) {
                    edgeTo[w] = v;
                    marked[w] = true;
                    queue.add(w);
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
        BreadthFirstPaths bfs = new BreadthFirstPaths(G, s);
        for(int v = 0; v < G.V(); v++) {
            if(bfs.hasPathTo(v)) {
                System.out.printf("%d to %d:  ", s, v);
                for(int x : bfs.pathTo(v)) {
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
