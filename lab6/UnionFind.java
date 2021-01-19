public class UnionFind {

    // TODO - Add instance variables?
    private int[] union;

    /* Creates a UnionFind data structure holding n vertices. Initially, all
       vertices are in disjoint sets. */
    public UnionFind(int n) {
        union = new int[n];
        for (int i = 0; i < n; i++) {
            union[i] = -1;
        }
    }

    /* Throws an exception if v1 is not a valid index. */
    private void validate(int vertex) {
        if (vertex<0 || vertex>=union.length) {
            throw new IllegalArgumentException(vertex + " is not a valid index");
        }
    }

    /* Returns the size of the set v1 belongs to. */
    public int sizeOf(int v1) {
        validate(v1);
        return -union[find(v1)];
    }

    /* Returns the parent of v1. If v1 is the root of a tree, returns the
       negative size of the tree for which v1 is the root. */
    public int parent(int v1) {
        validate(v1);
        return union[v1];
    }

    /* Returns true if nodes v1 and v2 are connected. */
    public boolean connected(int v1, int v2) {
        validate(v1);
        validate(v2);
        if (find(v1) == find(v2)){
            return true;
        }
        return false;
    }

    /* Connects two elements v1 and v2 together. v1 and v2 can be any valid 
       elements, and a union-by-size heuristic is used. If the sizes of the sets
       are equal, tie break by connecting v1's root to v2's root. Unioning a 
       vertex with itself or vertices that are already connected should not 
       change the sets but may alter the internal structure of the data. */
    public void union(int v1, int v2) {
        validate(v1);
        validate(v2);
        if (connected(v1,v2)) {
            return;
        }
        if (sizeOf(v1)>=sizeOf(v2)) {
            int n1 = find(v1);
            int n2 = find(v2);
            int size1 = parent(n1);
            int size2 = parent(n2);
            union[n2] = n1;
            union[n1] = size1 + size2;
        } else {
            union(v2, v1);
        }
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. */
    public int find(int vertex) {
        validate(vertex);
        if (parent(vertex) < 0) {
            return vertex;
        }
        return find(parent(vertex));
    }

    public static void main(String[] args) {
        UnionFind u = new UnionFind(10);
        u.union(1,2);
        u.union(3,4);
        u.union(1,3);
        System.out.println(u.sizeOf(2));
        System.out.println(u.parent(3));
        u.validate(10);

    }
}
