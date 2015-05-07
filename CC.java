import java.util.*;

public class CC {
    private boolean[] marked;
    private int[] id;
    private int[] size;
    private int count;
    public CC(Graph G) {
        marked = new boolean[G.V()];
        id = new int[G.V()];
        size = new int[G.V()];
        for(int v = 0; v < G.V(); v++) {
            if(!marked[v]) {
                dfs(G, v);
                count++;
            }
        }
    }
    private void dfs(Graph G, int v) {
        marked[v] = true;
        id[v] = count;
        size[count]++;
        for(int w : G.adj(v)) {
            if(!marked[w])
                dfs(G, w);
        }
    }
    public int id(int v) {
        return id[v];
    }
    public int size(int v) {
        return size[id[v]];
    }
    public int count() {
        return count;
    }
    public boolean connected(int v, int w) {
        return id(v) == id(w);
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Graph G = new Graph(scanner);
        CC cc = new CC(G);
        int M = cc.count();
        System.out.println(M + " components");
        Queue<Integer>[] components = (Queue<Integer>[]) new Queue[M];
        for(int i = 0; i < M; i++)
            components[i] = new Queue<Integer>();
        for(int v = 0; v < G.V(); v++)
            components[cc.id(v)].add(v);
        for(int i = 0; i < M; i++) {
            for(int v : components[i]) {
                System.out.print(v + " ");
            }
            System.out.println();
        }
    }
}
