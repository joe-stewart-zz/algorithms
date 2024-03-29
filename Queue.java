import java.util.*;

public class Queue<Item> implements Iterable<Item> {
    private int N;
    private Node<Item> first;
    private Node<Item> last;
    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
    }
    public Queue() {
        first = null;
        last = null;
        N = 0;
    }
    public boolean isEmpty() {
        return first == null;
    }
    public int size() {
        return N;
    }
    public void add(Item item) {
        Node<Item> oldlast = last;
        last = new Node<Item>();
        last.item = item;
        last.next = null;
        if(isEmpty())
            first = last;
        else
            oldlast.next = last;
        N++;
    }
    public Item remove() {
        if(isEmpty())
            throw new NoSuchElementException("Queue underflow");
        Item item = first.item;
        first = first.next;
        N--;
        if(isEmpty())
            last = null;
        return item;
    }
    public String toString() {
        StringBuilder s = new StringBuilder();
        for(Item item : this)
            s.append(item + " ");
        return s.toString();
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
        Queue<String> q = new Queue<>();
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()) {
            String item = scanner.next();
            if(!item.equals("-"))
                q.add(item);
            else if(!q.isEmpty())
                System.out.print(q.remove() + " ");
        }
        System.out.println("(" + q.size() + " left on queue)");
    }
}
