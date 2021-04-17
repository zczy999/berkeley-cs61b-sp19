package bearmaps.proj2ab;

import java.util.ArrayList;
import java.util.List;

public class MyTrieSet implements TrieSet61B {

    private static final int R = 128; // ASCII
    private node root;


    /**
     * Clears all items out of Trie
     */
    @Override
    public void clear() {
        root = null;
    }

    /**
     * Returns true if the Trie contains KEY, false otherwise
     *
     * @param key
     */
    @Override
    public boolean contains(String key) {
        if (root == null) return false;
        return contains(root, key, 0);
    }

    private boolean contains(node root,String key,int d) {
        if (root == null) return false;
        if (key.length() == d) {
            if (!root.isKey) {
                return false;
            }else return true;
        }
        char c = key.charAt(d);
        return contains(root.next[c],key,d+1);
    }

    /**
     * Inserts string KEY into Trie
     *
     * @param key
     */
    @Override
    public void add(String key) {
        root = add(root,key,0);
    }

    private node add(node n,String key,int d) {
        if (n == null) n = new node();
        if (key.length() == d){
            n.isKey = true;
            return n;
        }
        char c = key.charAt(d);
        n.next[c] = add(n.next[c],key,d+1);
        return n;
    }

    /**
     * Returns a list of all words that start with PREFIX
     *
     * @param prefix
     */
    @Override
    public List<String> keysWithPrefix(String prefix) {
        node prefixNext = findPrefixNext(root,prefix,0);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < R; i++) {
            if (prefixNext.next[i] != null) {
                char c = (char) i;
                String news = prefix + c;
                colHelp(news, list, prefixNext.next[i]);
            }
        }
        return list;
    }

    private void colHelp(String x, List list, node n) {
        if (n.isKey) list.add(x);
        for (int i = 0; i < R; i++) {
            if (n.next[i] != null) {
                char c = (char) i;
                String news = x + c;
                colHelp(news, list, n.next[i]);
            }
        }
    }

    private node findPrefixNext(node root,String prefix,int d) {
        if (root == null) return null;
        if (prefix.length() == d) {
            return root;
        }
        char c = prefix.charAt(d);
        return findPrefixNext(root.next[c],prefix,d+1);
    }


    /**
     * Returns the longest prefix of KEY that exists in the Trie
     * Not required for Lab 9. If you don't implement this, throw an
     * UnsupportedOperationException.
     *
     * @param key
     */
    @Override
    public String longestPrefixOf(String key) {
        StringBuilder result = new StringBuilder();
        collect(root, key, result);
        return result.toString();
    }

    private void collect(node node, String key, StringBuilder res) {
        if(node == null) return;
        for(int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            if(node.next[c] != null) {
                res.append(c);
                node = node.next[c];
            } else return;
        }
    }

    private class node {
        private boolean isKey;
        private node[] next = new node[R];
    }

    public static void main(String[] args) {
        String s = "test";
        MyTrieSet myTrieSet = new MyTrieSet();
        myTrieSet.add(s);
        System.out.println(myTrieSet.contains(s));
        myTrieSet.keysWithPrefix("tes");
        System.out.println(myTrieSet.longestPrefixOf("te"));;
    }
}
