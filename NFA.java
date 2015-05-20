public class NFA {
    private Digraph G;
    private String regex;
    private int M;
    public NFA(String regex) {
        this.regex = regex;
        M = regex.length();
        Stack<Integer> ops = new Stack<Integer>();
        G = new Digraph(M+1);
        for(int i = 0; i < M; i++) {
            int lp = i;
            if(regex.charAt(i) == '(' || regex.charAt(i) == '|')
                ops.push(i);
            else if(regex.charAt(i) == ')') {
                int or = ops.pop();
                if(regex.charAt(or) == '|') {
                    lp = ops.pop();
                    G.addEdge(lp, or+1);
                    G.addEdge(or, i);
                } else if(regex.charAt(or) == '(')
                    lp = or;
            }
            if(i < M-1 && regex.charAt(i+1) == '*') {
                G.addEdge(lp, i+1);
                G.addEdge(i+1, lp);
            }
            if(regex.charAt(i) == '(' || regex.charAt(i) == '*' || regex.charAt(i) == ')')
                G.addEdge(i, i+1);
        }
    }
    public boolean recognizes(String txt) {
        DirectedDFS dfs = new DirectedDFS(G, 0);
        Bag<Integer> pc = new Bag<Integer>();
        for(int v = 0; v < G.V(); v++)
            if(dfs.marked(v))
                pc.add(v);
        for(int i = 0; i < txt.length(); i++) {
            Bag<Integer> match = new Bag<Integer>();
            for(int v : pc) {
                if(v == M)
                    continue;
                if((regex.charAt(v) == txt.charAt(i)) || regex.charAt(v) == '.')
                    match.add(v+1);
            }
            dfs = new DirectedDFS(G, match);
            pc = new Bag<Integer>();
            for(int v = 0; v < G.V(); v++)
                if(dfs.marked(v))
                    pc.add(v);
            if(pc.size() == 0)
                return false;
        }
        for(int v : pc)
            if(v == M)
                return true;
        return false;
    }
    public static void main(String[] args) {
        String regex = "(" + args[0] + ")";
        String txt = args[1];
        if(txt.indexOf('|') >= 0)
            throw new IllegalArgumentException("| character in text is not supported");
        NFA nfa = new NFA(regex);
        System.out.println(nfa.recognizes(txt));
    }
}
