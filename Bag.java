import java.util.*;

public class Bag<Item> implements Iterable<Item> {
    private int N;
    private Node<Item> first;
    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
    }
    public Bag() {
        first = null;
        N = 0;
    }
    public boolean isEmpty() {
        return first == null;
    }
    public int size() {
        return N;
    }
    public void add(Item item) {
        Node<Item> oldfirst = first;
        first = new Node<Item>();
        first.item = item;
        first.next = oldfirst;
        N++;
    }
    public Iterator<Item> iterator() {
        return new ListIterator<Item>(first);
    }
    private class ListIterator<Item> implements Iterator<Item> {
        private Node<Item> current;
        public ListIterator(Node<Item> first) {
            current = first;
        }
        public boolean hasNext() {
            return current != null;
        }
        public Item next() {
            if(!hasNext())
                throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    public static void main(String[] args) {
        Bag<String> b = new Bag<>();
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()) {
            String item = scanner.next();
            b.add(item);
        }
        System.out.println("size of bag = " + b.size());
        for(String s : b)
            System.out.println(s);
    }
}
