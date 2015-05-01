import java.util.*;

public class Shell {
    private Shell() {}
    public static void sort(Comparable[] a) {
        int N = a.length;
        int h = 1;
        while(h < N/3)
            h = 3*h + 1;
        while(h >= 1) {
            for(int i = h; i < N; i++) {
                for(int j = i; j >= h && less(a[j], a[j-h]); j -= h) {
                    exch(a, j, j-h);
                }
            }
            h /= 3;
        }
    }
    private static boolean less(Comparable v, Comparable w) {
        return (v.compareTo(w) < 0);
    }
    private static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
    private static void show(Comparable[] a) {
        for(int i = 0; i < a.length; i++) {
            System.out.println(a[i]);
        }
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> list = new ArrayList<>();
        while(scanner.hasNext()) {
            String item = scanner.next();
            list.add(item);
        }
        String[] sa = new String[list.size()];
        list.toArray(sa);
        Shell.sort(sa);
        show(sa);
    }
}
