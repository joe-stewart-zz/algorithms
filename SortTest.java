import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.*;

import java.util.*;

public class SortTest {
    static List<String> list;
    static String[] a;

    @BeforeClass
    public static void setup() {
        list = Arrays.asList("cherries", "apples", "oranges", "bananas", "watermelon");
        a = new String[list.size()];;
        list.toArray(a);
    }
    @Test
    public void testHeapSort() {
        Heap.sort(a);
        assertEquals("cherries", a[2]);
    }
    @Test
    public void testQuickSort() {
        Quick.sort(a);
        assertEquals("cherries", a[2]);
    }
    @Test
    public void testMergeSort() {
        Merge.sort(a);
        assertEquals("cherries", a[2]);
    }
    @Test
    public void testShellSort() {
        Shell.sort(a);
        assertEquals("cherries", a[2]);
    }
    @Ignore @Test
    public void testInsertionSort() {
        Insertion.sort(a);
        assertEquals("cherries", a[2]);
    }
    @Ignore @Test
    public void testSelectionSort() {
        Selection.sort(a);
        assertEquals("cherries", a[2]);
    }
}
