import java.util.*;

public class DepthFirstOrder {
    private boolean[] marked;
    private int[] pre;
    private int[] post;
    private Queue<Integer> preorder;
    private Queue<Integer> postorder;
    private int preCounter;
    private int postCounter;
    public DepthFirstOrder(Digraph G) {
        pre = new int[G.V()];
        post = new int[G.V()];
        postorder = new Queue<Integer>();
        preorder = new Queue<Integer>();
        marked = new boolean[G.V()];
        for(int v = 0; v < G.V(); v++)
            if(!marked[v])
                dfs(G, v);
    }
    public DepthFirstOrder(EdgeWeightedDigraph G) {
        pre = new int[G.V()];
        post = new int[G.V()];
        postorder = new Queue<Integer>();
        preorder = new Queue<Integer>();
        marked = new boolean[G.V()];
        for(int v = 0; v < G.V(); v++)
            if(!marked[v])
                dfs(G, v);
    }
    private void dfs(Digraph G, int v) {
        marked[v] = true;
        pre[v] = preCounter++;
        preorder.add(v);
        for(int w : G.adj(v))
            if(!marked[w])
                dfs(G, w);
        postorder.add(v);
        post[v] = postCounter++;
    }
    private void dfs(EdgeWeightedDigraph G, int v) {
        marked[v] = true;
        pre[v] = preCounter++;
        preorder.add(v);
        for(DirectedEdge e : G.adj(v)){
            int w = e.to();
            if(!marked[w])
                dfs(G, w);
        }
        postorder.add(v);
        post[v] = postCounter++;
    }
    public int pre(int v) {
        return pre[v];
    }
    public int post(int v) {
        return post[v];
    }
    public Iterable<Integer> post() {
        return postorder;
    }
    public Iterable<Integer> pre() {
        return preorder;
    }
    public Iterable<Integer> reversePost() {
        Stack<Integer> reverse = new Stack<Integer>();
        for(int v : postorder)
            reverse.push(v);
        return reverse;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Digraph G = new Digraph(scanner);
        DepthFirstOrder dfs = new DepthFirstOrder(G);
        System.out.println("   v  pre post");
        System.out.println("______________");
        for(int v = 0; v < G.V(); v++)
            System.out.printf("%4d %4d %4d\n", v, dfs.pre(v), dfs.post(v));
        System.out.print("Preorder: ");
        for(int v : dfs.pre())
            System.out.print(v + " ");
        System.out.println();
        System.out.print("Postorder: ");
        for(int v : dfs.post())
            System.out.print(v + " ");
        System.out.println();
        System.out.print("Reverse postorder: ");
        for(int v : dfs.reversePost())
            System.out.print(v + " ");
        System.out.println();
    }
}
