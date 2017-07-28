package com.google.engedu.ghost;

import java.util.HashMap;


public class TrieNode {
    private HashMap<Character, TrieNode> children;
    private boolean isWord;
    

    public TrieNode() {
        children = new HashMap<>();
        isWord = false;
    }

    public void add(String s) {

    }
    
    

    public boolean isWord(String s) {
		return true;
    }

    public String getAnyWordStartingWith(String s) {
        return null;
    }

    public String getGoodWordStartingWith(String s) {
        return null;
    }
}
