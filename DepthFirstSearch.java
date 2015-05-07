import java.util.*;

public class DepthFirstSearch {
    private boolean[] marked;
    private int count;
    public DepthFirstSearch(Graph G, int s) {
        marked = new boolean[G.V()];
        dfs(G, s);
    }
    private void dfs(Graph G, int v) {
        count++;
        marked[v] = true;
        for(int w : G.adj(v))
            if(!marked[w])
                dfs(G, w);
    }
    public boolean marked(int v) {
        return marked[v];
    }
    public int count() {
        return count;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Graph G = new Graph(scanner);
        int s = Integer.parseInt(args[0]);
        DepthFirstSearch dfs = new DepthFirstSearch(G, s);
        for(int v = 0; v < G.V(); v++)
            if(dfs.marked(v))
                System.out.print(v + " ");
        System.out.println();
        if(dfs.count() != G.V())
            System.out.println("Not connected");
        else
            System.out.println("Connected");
    }
}
