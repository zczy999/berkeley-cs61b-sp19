import java.util.ArrayList;

public class LinkedListDeque<T> {
    public class IntNode {
        public T item;
        public IntNode next;
        public IntNode prew;
        public IntNode(T i, IntNode n,IntNode p) {
            item = i;
            next = n;
            prew = p;
        }
    }

    private int size;
    private IntNode sentinel;

    public LinkedListDeque() {
        sentinel = new IntNode(null,null, null);
        sentinel.next = sentinel;
        sentinel.prew = sentinel;
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return (size == 0);
    }

    public void addFirst(T item) {
        size += 1;
        sentinel.next = new IntNode(item, sentinel.next, sentinel);
        if(sentinel.next.next != sentinel) {
            sentinel.next.next.prew = sentinel.next;
        }
        if (sentinel.prew == sentinel) {
            sentinel.prew = sentinel.next;
        }
    }

    public void addLast(T item) {
        size += 1;
        sentinel.prew = new IntNode(item, sentinel, sentinel.prew);
        if (sentinel.prew.prew != sentinel) {
            sentinel.prew.prew.next = sentinel.prew;
        }
        if (sentinel.next == sentinel) {
            sentinel.next = sentinel.prew;
        }
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        size -= 1;
        T t = sentinel.next.item;
        if (sentinel.next.next != sentinel){
            sentinel.next = sentinel.next.next;
            sentinel.next.prew = sentinel;
        }else {
            sentinel.next = sentinel;
            sentinel.prew = sentinel;
        }
        return t;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        size -= 1;
        T t = sentinel.prew.item;
        if (sentinel.prew.prew != sentinel){
            sentinel.prew = sentinel.prew.prew;
            sentinel.prew.next = sentinel;
        }else {
            sentinel.prew = sentinel;
            sentinel.next = sentinel;
        }
        return t;
    }

    public T get(int index) {
        IntNode p = sentinel;
        while (index > 0) {
            p = p.next;
            index--;
        }
        if (p == sentinel) {
            return null;
        }
        return p.item;
    }

    public void printDeque() {
        IntNode p = sentinel;
        while (p.next != sentinel){
            p = p.next;
            System.out.print(p.item + " ");
        }
        System.out.print("\n");
    }

    public static void main(String[] args) {
        LinkedListDeque<Integer> test = new LinkedListDeque<>();
        test.addFirst(1);
        test.addFirst(2);
        test.addLast(3);
        test.printDeque();
        System.out.println(test.get(1));
        test.removeFirst();
        test.removeLast();
        test.removeLast();
    }
}
