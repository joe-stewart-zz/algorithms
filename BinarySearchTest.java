import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.*;

import java.util.*;

public class BinarySearchTest {
    static List<String> list;
    static String[] a;
    static String key;
    static int result;

    @Before
    public void setup() {
        list = Arrays.asList("cherries", "apples", "oranges", "bananas", "watermelon");
    }
    @Test
    public void testEmptyArray() {
        a = new String[0];
        key = "pears";
        result = BinarySearch.search(a, key);
        assertEquals(-1, result);
    }
    @Test
    public void testFound() {
        a = new String[list.size()];
        list.toArray(a);
        Quick.sort(a);
        key = "cherries";
        result = BinarySearch.search(a, key);
        assertTrue(result > 0);
    }
    @Test
    public void testNotFound() {
        a = new String[list.size()];
        list.toArray(a);
        Quick.sort(a);
        key = "pears";
        result = BinarySearch.search(a, key);
        assertTrue(result < 0);
    }
}
