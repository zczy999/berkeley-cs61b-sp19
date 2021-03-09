import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MyHashMap<K, V> implements Map61B<K, V>{

    private int size;
    private int threshold;
    private double loadFactor;
    private Entry<K, V>[] bins;

    /**
     * Represents one node in the linked list that stores the key-value pairs
     * in the dictionary.
     */
    private class Entry<K, V> {

        /**
         * Stores KEY as the key in this key-value pair, VAL as the value, and
         * NEXT as the next node in the linked list.
         */
        Entry(K k, V v, Entry n) {
            key = k;
            val = v;
            next = n;
        }

        /**
         * Returns the Entry in this linked list of key-value pairs whose key
         * is equal to KEY, or null if no such Entry exists.
         */
        Entry get(K k) {
            if (k != null && k.equals(key)) {
                return this;
            }
            if (next == null) {
                return null;
            }
            return next.get(k);
        }

        Entry remove(K k) {
            if (k != null && next!=null && k.equals(next.key)) {
                Entry temp = next;
                next = next.next;
                return temp;
            }
            if (next == null) {
                return null;
            }
            return next.remove(k);
        }

        /** Stores the key of the key-value pair of this node in the list. */
        K key;
        /** Stores the value of the key-value pair of this node in the list. */
        V val;
        /** Stores the next Entry in the linked list. */
        Entry next;

    }

    public MyHashMap(int size, double loadFactor) {
        if (size < 1 || loadFactor < 0 ){
            throw new IllegalArgumentException();
        }
        bins = new Entry[size];
        this.loadFactor = loadFactor;
        this.size = 0;
        this.threshold = (int) (loadFactor*bins.length);
    }

    public MyHashMap(int size) {
        this(size,0.75);
    }

    public MyHashMap() {
        this(16, 0.75);
    }

    /**
     * Removes all of the mappings from this map.
     */
    @Override
    public void clear() {
        size = 0;
        bins = null;
    }

    /**
     * Returns true if this map contains a mapping for the specified key.
     *
     * @param key
     */
    @Override
    public boolean containsKey(K key) {
        V temp = get(key);
        if (temp == null) return false;
        return true;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     *
     * @param key
     */
    @Override
    public V get(K key) {
        if (size == 0) return null;
        int hashcode = key.hashCode();
//        int n = Math.floorMod(hashcode, bins.length);
        int n;
        if (bins[n = (bins.length-1) & hashcode] == null) {
            return null;
        }
        Entry temp = bins[n];
        Entry k = temp.get(key);
        return (V) k.val;
    }

    /**
     * Returns the number of key-value mappings in this map.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key,
     * the old value is replaced.
     *
     * @param key
     * @param value
     */
    @Override
    public void put(K key, V value) {
        int hashcode = key.hashCode();
        int n = Math.floorMod(hashcode, bins.length);
        if (bins[n] == null) {
            bins[n] = new Entry<>(key, value, null);
            size += 1;
        } else {
            Entry temp = bins[n];
            Entry k = temp.get(key);
            if (k == null){
                bins[n] = new Entry<>(key, value, temp);
                size += 1;
            } else {
                k.val = value;
            }
        }
        //扩容判断
        if (size > threshold) resize();
    }

    private void resize() {
        Entry<K, V>[] newbins = new Entry[bins.length*2];
        for (Entry<K, V> e:bins) {
            if (e == null) continue;
            while (e != null) {
                int hash = Math.floorMod(e.key.hashCode(), bins.length*2);;
                if (newbins[hash] == null) {
                    newbins[hash] = new Entry<>(e.key, e.val, null);
                } else {
                    Entry temp = newbins[hash];
                    newbins[hash] = new Entry<>(e.key, e.val, temp);;
                }
                e = e.next;
            }
        }
        bins = newbins;
        threshold = (int) (loadFactor*bins.length);
    }

    /**
     * Returns a Set view of the keys contained in this map.
     */
    @Override
    public Set<K> keySet() {
        HashSet<K> keySet = new HashSet();
        for (K e: this) {
            keySet.add(e);
        }
        return keySet;
    }

    /**
     * Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException.
     *
     * @param key
     */
    @Override
    public V remove(K key) {
        if (size == 0) return null;
        int hashcode = key.hashCode();
        int n = Math.floorMod(hashcode, bins.length);
        if (bins[n] == null) {
            return null;
        }
        Entry temp = bins[n];
        if (temp.next == null) {
            bins[n] = null;
            size -= 1;
            return (V) temp.val;
        }
        Entry k = temp.remove(key);
        size -= 1;
        return (V) k.val;
    }

    /**
     * Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.
     *
     * @param key
     * @param value
     */
    @Override
    public V remove(K key, V value) {
        return null;
    }

    private class MyHashMaoIterator implements Iterator<K> {
        private int pos;
        private int arrayPos;
        private Entry entry;
        private MyHashMaoIterator() {
            pos = 0;
            arrayPos = 0;
            entry = null;
        }

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return pos < size;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         */
        @Override
        public K next() {
            while (arrayPos<bins.length && bins[arrayPos] == null) {
                arrayPos += 1;
            }
            if (entry != null) {
                Entry temp = entry;
                entry = entry.next;
                if (entry == null) arrayPos += 1;
                pos += 1;
                return (K) temp.key;
            }
            Entry temp = bins[arrayPos];
            entry = temp.next;
            if (entry == null) arrayPos += 1;
            pos += 1;
            return (K) temp.key;
        }
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<K> iterator() {
        return new MyHashMaoIterator();
    }
}
