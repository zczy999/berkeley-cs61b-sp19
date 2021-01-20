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
        return null;
    }

    @Override
    public V remove(K key) {
        return null;
    }

    @Override
    public V remove(K key, V value) {
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return null;
    }
}
