import java.util.*;

public class BinarySearch {
    private BinarySearch() {}

    public static int search(Object[] a, Comparable key) {
        return search(a, key, 0, a.length - 1);
    }
    public static int search(Object[] a, Comparable key, int lo, int hi) {
        if(lo > hi)
            return -1;
        int mid = lo + (hi - lo) / 2;
        if(key.compareTo(a[mid]) < 0)
            return search(a, key, lo, mid - 1);
        else if(key.compareTo(a[mid]) > 0)
            return search(a, key, mid + 1, hi);
        else
            return mid;
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
        Quick.sort(sa);

        System.out.println();
        String key = "that";
        int result = search(sa, key);
        if(result > 0)
            System.out.println("found key: " + key + " at index: " + result);
        else
            System.out.println("did not find key: " + key);
    }
}
