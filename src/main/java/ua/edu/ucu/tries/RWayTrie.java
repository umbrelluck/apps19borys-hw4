package ua.edu.ucu.tries;

import ua.edu.ucu.queue.Queue;

import java.util.*;

public class RWayTrie implements Trie {

    private ArrayList<RWayTrie> children;
    private char value;
    private int end;

    public RWayTrie() {
        children = new ArrayList<>();
        value = '0';
        end = 0;
    }

    public RWayTrie(char val) {
        children = new ArrayList<>();
        value = val;
        end = 0;
    }

    @Override
    public void add(Tuple t) {
        if (t.weight > 2) {
            RWayTrie tree = this;
            boolean fl;
            for (int i = 0; i < t.term.length(); i++) {
                fl = true;
                char chr = t.term.charAt(i);
                for (RWayTrie child : tree.children) {
                    if (child.value == chr) {
                        fl = false;
                        tree = child;
                        break;
                    }
                }
                if (fl) {
                    int index = tree.children.size();
                    tree.children.add(new RWayTrie(chr));
                    tree = tree.children.get(index);
                }
            }
            tree.end = t.weight;
        }
    }

    @Override
    public boolean contains(String word) {
        RWayTrie tree = get(word);
        if (tree == null) {
            return false;
        } else {
            return tree.end == word.length();
        }
    }

    private RWayTrie get(String word) {
        RWayTrie tree = this;
        for (int i = 0; i < word.length(); i++) {
            char chr = word.charAt(i);
            for (RWayTrie child : tree.children) {
                if (child.value == chr) {
                    tree = child;
                } else {
                    return null;
                }
            }
        }
        return tree;
    }

    @Override
    public boolean delete(String word) {
        RWayTrie tree = this;
        Stack<RWayTrie> stack = new Stack<>();
        for (int i = 0; i < word.length(); i++) {
            char chr = word.charAt(i);
            for (RWayTrie child : tree.children) {
                if (child.value == chr) {
                    stack.add(child);
                    tree = child;
                } else {
                    return false;
                }
            }
        }
        stack.pop();
        RWayTrie previous = stack.pop();
        if (tree.value == 0) {
            return false;
        }
        while (tree.children.isEmpty() && (tree.value == 0 || tree.value ==
                word.length())) {
            previous.children.remove(tree);
            tree = previous;
            previous = stack.pop();
        }
        return true;
    }

    @Override
    public Iterable<String> words() {
        return wordsWithPrefix("");
    }

    @Override
    public Iterable<String> wordsWithPrefix(String s) {
        Queue q = new Queue();
        collect(get(s), s, q);
        if (!contains(s)) {
            q.dequeue();
        }
        Object[] tmp = q.toArray();
        String[] res = Arrays.copyOf(tmp, tmp.length, String[].class);
        Arrays.sort(res, Comparator.comparingInt(String::length));
        return () -> Arrays.stream(res).iterator();
    }

    private void collect(RWayTrie tree, String pre, Queue q) {
        if (tree == null) return;
        if (tree.value != '0') q.enqueue(pre);
        for (RWayTrie child : tree.children)
            collect(child, pre + child.value, q);
    }

    @Override
    public int size() {
        int sz = (end != 0) ? 1 : 0;
        for (RWayTrie elem : children) {
            sz += elem.size();
        }
        return sz;
    }
}
