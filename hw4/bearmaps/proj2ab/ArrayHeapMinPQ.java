package bearmaps.proj2ab;

import java.util.*;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T>{
    private List<PriorityNode> items;
    private int lastNodePosition;
    private Map<T, Integer> itemMapIndex;

    public ArrayHeapMinPQ() {
        items = new ArrayList<>();
        items.add(null);
        itemMapIndex = new HashMap<>();
        lastNodePosition = 0;
    }

    @Override
    public void add(T item, double priority) {
        if (contains(item)) throw new IllegalArgumentException("the item already exists");
        lastNodePosition += 1;
        items.add(new PriorityNode(item, priority));
        itemMapIndex.put(item, lastNodePosition);
        switchBasePositionByPriority(lastNodePosition);
    }

    private int getParentNodePosition(int pos) {
        return pos/2;
    }

    private void switchBasePositionByPriority(int pos) {
        int parentNodePos = getParentNodePosition(pos);
        if (parentNodePos == 0) return;
        if (items.get(pos).priority < items.get(parentNodePos).priority) {
            exchNode(pos, parentNodePos);
            switchBasePositionByPriority(parentNodePos);
        }
    }

    private void switchTopPositionByPriority(int pos) {
        int leftChild = pos * 2;
        int rightChild = pos * 2 + 1;
        if (size() < leftChild) return;
        if (items.get(leftChild).priority < items.get(pos).priority) {
            exchNode(leftChild, pos);
            switchTopPositionByPriority(leftChild);
        }
        if (size() < rightChild) return;
        if (items.get(rightChild).priority < items.get(pos).priority) {
            exchNode(rightChild, pos);
            switchTopPositionByPriority(rightChild);
        }
    }

    private void exchNode(int m,int n) {
        PriorityNode tem = items.get(m);
        items.set(m, items.get(n));
        items.set(n, tem);
        itemMapIndex.put(items.get(m).item, m);
        itemMapIndex.put(items.get(n).item, n);
    }

    @Override
    public boolean contains(T item) {
        return itemMapIndex.get(item)!=null;
    }

    @Override
    public T getSmallest() {
        if (size()==0) throw new NoSuchElementException();
        return items.get(1).getItem();
    }

    @Override
    public T removeSmallest() {
        if (size()==0) throw new NoSuchElementException();
        T tem = items.get(1).getItem();
        items.set(1, items.get(lastNodePosition));
        items.remove(lastNodePosition);
        lastNodePosition -= 1;
        switchTopPositionByPriority(1);
        return tem;
    }

    @Override
    public int size() {
        return lastNodePosition;
    }

    @Override
    public void changePriority(T item, double priority) {
        Integer pos = itemMapIndex.get(item);
        if (pos == null) throw new NoSuchElementException();
        items.set(pos,new PriorityNode(item, priority));
    }

    private class PriorityNode implements Comparable<PriorityNode> {
        private T item;
        private double priority;

        PriorityNode(T e, double p) {
            this.item = e;
            this.priority = p;
        }

        T getItem() {
            return item;
        }

        double getPriority() {
            return priority;
        }

        void setPriority(double priority) {
            this.priority = priority;
        }

        @Override
        public int compareTo(PriorityNode other) {
            if (other == null) {
                return 1;
            }
            return Double.compare(this.getPriority(), other.getPriority());
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean equals(Object o) {
            if (o == null || o.getClass() != this.getClass()) {
                return false;
            } else {
                return ((PriorityNode) o).getItem().equals(getItem());
            }
        }

        @Override
        public int hashCode() {
            return item.hashCode();
        }
    }

}
