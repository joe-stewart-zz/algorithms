import java.util.*;

public class Util {
    private Util() {}
    public static void shuffle(Object[] a) {
        int N = a.length;
        for(int i = 0; i < N; i++) {
            int r = i + (int) (Math.random() * (N-i));
            Object swap = a[r];
            a[r] = a[i];
            a[i] = swap;
        }
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
        Util.shuffle(sa);
        show(sa);
    }
}
