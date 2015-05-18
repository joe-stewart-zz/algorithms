import java.util.*;

public class LSD {
    public static void sort(String[] a, int W) {
        int N = a.length;
        int R = 256;
        String[] aux = new String[N];
        for(int d = W-1; d >= 0; d--) {
            int[] count = new int[R+1];
            for(int i = 0; i < N; i++)
                count[a[i].charAt(d) + 1]++;
            for(int r = 0; r < R; r++)
                count[r+1] += count[r];
            for(int i = 0; i < N; i++)
                aux[count[a[i].charAt(d)]++] = a[i];
            for(int i = 0; i < N; i++)
                a[i] = aux[i];
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> list = new ArrayList<>();
        while(scanner.hasNext())
            list.add(scanner.next());
        String[] a = new String[list.size()];;
        list.toArray(a);
        int W = a[0].length();
        sort(a, W);
        for(String s : a)
            System.out.println(s);
    }
}
