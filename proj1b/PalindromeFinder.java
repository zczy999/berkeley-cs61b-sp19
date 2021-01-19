/** This class outputs all palindromes in the words file in the current directory. */
public class PalindromeFinder {
    public static void main(String[] args) {
        int minLength = 4;
        In in = new In("../library-sp19/data/words.txt");
        Palindrome palindrome = new Palindrome();
        OffByOne offByOne = new OffByOne();
        OffByN offByN = new OffByN(2);
        while (!in.isEmpty()) {
            String word = in.readString();
            if (word.length() >= minLength && palindrome.isPalindrome(word, offByN)) {
                System.out.println(word);
            }
        }
    }
}