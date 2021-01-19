public class ArrayDeque<T> {
    private int size;
    private T[] items;
    private int nextLast;
    private int nextFirst;

    public ArrayDeque() {
        size = 0;
        items = (T[]) new Object[8];
        nextLast = 0;
        nextFirst = items.length - 1;
    }

    public void addFirst(T item) {
        if (size == items.length){
            resize();
        }
        size += 1;
        items[nextFirst] = item;
        nextFirst -= 1;
        if (nextFirst < 0) {
            nextFirst = items.length + nextFirst;
        }
    }

    public void addLast(T item) {
        if (size == items.length){
            resize();
        }
        size += 1;
        items[nextLast] = item;
        nextLast += 1;
        if (nextLast > (items.length-1)){
            nextLast = nextLast - items.length;
        }
    }

    private void resize() {
        T[] temp = (T[]) new Object[size*2];
        System.arraycopy(items,nextFirst+1,temp,0,size-nextFirst-1);
        System.arraycopy(items,0,temp,size-nextFirst-1,nextLast);
        items = temp;
        nextLast = size;
        nextFirst = items.length - 1;
    }

    public boolean isEmpty() {
        return (size == 0);
    }

    public int size() {
        return size;
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        size -= 1;
        nextFirst += 1;
        if (nextFirst > (items.length - 1)) {
            nextFirst = nextFirst - items.length;
        }
        T removeFirst = items[nextFirst];
        items[nextFirst] = null;
        return removeFirst;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        size -= 1;
        nextLast -= 1;
        if (nextLast < 0) {
            nextLast = nextLast + items.length;
        }
        T removeLast = items[nextLast];
        items[nextLast] = null;
        return removeLast;
    }

    public T get(int index) {
        int location = index + nextFirst + 1;
        if(location > (items.length - 1)) {
            location = location - items.length;
        }
        return items[location];
    }

    public void printDeque() {
        int index = 0;
        while (get(index) != null && index <= items.length) {
            System.out.print(get(index) + " ");
            index++;
        }
        System.out.print("\n");
    }

    public static void main(String[] args) {
        ArrayDeque<Integer> a = new ArrayDeque<>();
        a.addFirst(1);
        a.addFirst(2);
        a.addLast(3);
        a.addLast(4);
        a.addLast(5);
        a.addLast(6);
        a.addLast(7);
        a.addFirst(8);
        System.out.println(a.get(4));
//        a.addFirst(9);
        System.out.println(a.get(0));
        a.printDeque();
        a.removeFirst();
        a.removeFirst();
        a.removeFirst();
        a.removeLast();
        a.removeFirst();
        a.removeFirst();
        a.removeFirst();
        a.removeLast();
        a.addFirst(1);
        a.addFirst(2);
        a.addLast(3);
        a.addLast(4);
        a.addLast(5);
        a.addLast(6);
        a.addLast(7);
        a.addLast(8);
        a.addFirst(9);
    }
}
