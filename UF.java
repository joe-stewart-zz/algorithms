import java.util.*;

public class UF {
    private int[] id;
    private byte[] rank;
    private int count;
    public UF(int N) {
        if(N < 0)
            throw new IllegalArgumentException();
        count = N;
        id = new int[N];
        rank = new byte[N];
        for(int i = 0; i < N; i++) {
            id[i] = i;
            rank[i] = 0;
        }
    }
    public int find(int p) {
        if(p < 0 || p >= id.length)
            throw new IndexOutOfBoundsException();
        while(p != id[p]) {
            id[p] = id[id[p]];
            p = id[p];
        }
        return p;
    }
    public int count() {
        return count;
    }
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }
    public void union(int p, int q) {
        int i = find(p);
        int j = find(q);
        if(i == j)
            return;
        if(rank[i] < rank[j])
            id[i] = j;
        else if(rank[i] > rank[j])
            id[j] = i;
        else {
            id[j] = i;
            rank[i]++;
        }
        count--;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        UF uf = new UF(N);
        while(scanner.hasNext()) {
            int p = scanner.nextInt();
            int q = scanner.nextInt();
            if(uf.connected(p, q))
                continue;
            uf.union(p, q);
            System.out.println(p + " " + q);
        }
        System.out.println(uf.count() + " components");
    }
}
