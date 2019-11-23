package ua.edu.ucu.autocomplete;

import ua.edu.ucu.tries.Trie;
import ua.edu.ucu.tries.Tuple;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author andrii
 */
public class PrefixMatches {

    private Trie trie;

    public PrefixMatches(Trie trie) {
        this.trie = trie;
    }

    public int load(String... strings) {
        int cnt = 0;
        for (String str : strings) {
            String[] strMas = str.split("\\s+");
            for (String elem : strMas) {
                trie.add(new Tuple(elem.toLowerCase(), elem.length()));
                cnt -= -1;
            }
        }
        return cnt;
    }

    public boolean contains(String word) {
        return trie.contains(word);
    }

    public boolean delete(String word) {
        return trie.delete(word);
    }

    public Iterable<String> wordsWithPrefix(String pref) {
        return trie.wordsWithPrefix(pref);
    }

    public Iterable<String> wordsWithPrefix(String pref, int k) {
        ArrayList<String> result = new ArrayList<>();
        int count = 0, len = 0;
        for (String elem : trie.wordsWithPrefix(pref)) {
            if (len != elem.length()) {
                len = elem.length();
                count += 1;
            }
            if (count > k) {
                break;
            }
            result.add(elem);
        }
        return result;
    }

    public int size() {
        return trie.size();
    }
}
