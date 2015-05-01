import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.*;

import java.util.*;

public class BagTest {
    static List<String> list;
    static Bag<String> bag;

    @BeforeClass
    public static void setup() {
        list = Arrays.asList("apples", "oranges", "bananas", "watermelon");
        bag = new Bag<>();
    }
    @Test
    public void testAdd() {
        if(!bag.isEmpty())
            fail();
        for(String s : list)
            bag.add(s);
        assertEquals(4, bag.size());
    }
    @Test
    public void testIteration() {
        int i = 0;
        for(String s : bag)
            i++;
        assertEquals(bag.size(), i);
    }
}
