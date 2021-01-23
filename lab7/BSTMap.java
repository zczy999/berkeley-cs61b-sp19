import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V>{
    private Node root;

    private class Node {
        private K key;
        private V val;
        private Node left, right;
        // number of nodes in subtree
        private int size;

        public Node(K key,V val, int size) {
            this.key = key;
            this.val = val;
            this.size = size;
        }
    }

    @Override
    public void clear() {
        root = null;
    }

    @Override
    public boolean containsKey(K key) {
        Node temp = root;
        return containsNodeKey(temp, key);
    }

    private boolean containsNodeKey(Node x, K key) {
        if (x == null) {
            return false;
        }
        int comp = key.compareTo(x.key);
        if (comp < 0) {
            if (!containsNodeKey(x.left, key)) {
                return false;
            }
        }
        if (comp > 0) {
            if (!containsNodeKey(x.right, key)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public V get(K key) {
        Node temp = root;
        return getNode(temp, key);
    }

    private V getNode(Node x,K key) {
        if (x == null) {
            return null;
        }
        int comp = key.compareTo(x.key);
        V val = null;
        if (comp < 0) {
            val = getNode(x.left, key);
        } else if (comp == 0) {
            val = x.val;
        } else {
            val = getNode(x.right, key);
        }
        return val;
    }

    @Override
    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) {
            return 0;
        }
        return x.size;
    }

    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("calls put() with a null key");
        }
        if (value == null) {
            remove(key);
        }
        root = put(root, key, value);
    }

    private Node put(Node x, K key, V val) {
        if (x == null) {
            return new Node(key, val, 1);
        }
        int comp = key.compareTo(x.key);
        if (comp < 0) {
            x.left = put(x.left, key, val);
        } else if (comp == 0) {
            x.val = val;
        } else {
            x.right = put(x.right, key ,val);
        }
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    @Override
    public Set<K> keySet() {
        Set<K> kSet = new HashSet<>(size());
        for (K k: this) {
            kSet.add(k);
        }
        return kSet;
    }

    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("calls remove() with a null key");
        }
        V deleteIterm = get(key);
        root = remove(key, root);
        return deleteIterm;
    }

    private Node remove(K key, Node x) {
        if (x == null) {
            return null;
        }
        int comp = key.compareTo(x.key);
        if (comp < 0) {
            x.left = remove(key, x.left);
        } else if (comp > 0) {
            x.right = remove(key, x.right);
        } else {
            if (x.left == null) return x.right;
            if (x.right  == null) return x.left;
            Node t = x;
            x = maxNode(t.left);
            x.left = deleteMax(t.left);
            x.right = t.right;
        }
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    private Node deleteMax(Node x) {
        if (x.right == null) {
            return x.left;
        }
        x.right = deleteMax(x.right);
        x.size = x.left.size + x.right.size + 1;
        return x;
    }

    private Node maxNode(Node x) {
        if (x.right == null) {
            return x;
        }
        return maxNode(x.right);
    }

    @Override
    public V remove(K key, V value) {
        V deleteIterm = get(key);
        if (value.equals(deleteIterm)) {
            remove(key);
        }
        return value;
    }

    @Override
    public Iterator<K> iterator() {
        return new BSTMapIterator();
    }

    private class BSTMapIterator implements Iterator<K> {
        int cur;
        int rootsize;

        public BSTMapIterator() {
            cur = 0;
            rootsize = root.size;
        }

        @Override
        public boolean hasNext() {
            return cur < size();
        }

        @Override
        public K next() {
            Node tem = findNodeInOrder(root, cur);
            cur += 1;
            return tem.key;
        }
    }

    private Node findNodeInOrder(Node x, int k) {
        if (x == null) return null;
        if ( k>x.size) return null;
        int n = size(x.left);
        Node findNode;
        if (k < n) {
            findNode = findNodeInOrder(x.left, k);
        } else if (k > n) {
            // 右子树规模是 k - leftNodes - 1
            //     T
            //   a    c    --------       c
            // b  e  f  d               f   d
            findNode = findNodeInOrder(x.right,k-n-1);
        } else {
            return x;
        }
        return findNode;
    }

    public void printInOrder() {
        for (K k: this) {
            System.out.println(k);
        }
    }
}
