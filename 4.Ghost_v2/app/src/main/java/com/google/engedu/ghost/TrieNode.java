package com.google.engedu.ghost;

import java.util.HashMap;
import java.util.Random;


public class TrieNode {
    private HashMap<Character, TrieNode> children;
    private boolean isWord;

    public TrieNode() {
        children = new HashMap<>();
        isWord = false;
    }

    public void add(String s) {
        char curr;
        TrieNode trieNode=this;
        HashMap hashMap = this.children;

        for(int i=0;i<s.length();i++){
            curr = s.charAt(i);
            if(hashMap.containsKey(curr)){
                trieNode = (TrieNode) hashMap.get(curr);
            }
            else{
                trieNode = new TrieNode();
                hashMap.put(curr, trieNode);
            }
            hashMap = trieNode.children;
        }

        trieNode.isWord=true;
    }



    public boolean isWord(String s) {
        TrieNode trienode=this;
        HashMap hashMap = this.children;
        int i;
        for(i=0;i<s.length();i++){
            if(!(hashMap.containsKey(s.charAt(i)))){
                break;
            }
            trienode = (TrieNode) hashMap.get(s.charAt(i));
            hashMap = trienode.children;
        }

        if((trienode.isWord)){
            return true;
        }else{
            return false;
        }
    }

    public String getAnyWordStartingWith(String s) {
        TrieNode trieNode = this;
        HashMap hashMap = this.children;
        Random rm = new Random();
        char curr = '\0';
        int i;
        for(i=0;i<s.length();i++){
            curr = s.charAt(i);
            if(hashMap.containsKey(curr)){
                trieNode = (TrieNode) hashMap.get(curr);
                hashMap = trieNode.children;
            }
            else{
                break;
            }
        }

        if(i==s.length()){
            String validWord = s;

            while(trieNode.isWord!=true){
                curr=(char)(rm.nextInt(26)+97);
                while (!hashMap.containsKey(curr)){
                    curr=(char)(rm.nextInt(26)+97);
                }

                validWord = validWord + curr;
                trieNode=(TrieNode) hashMap.get(curr);
                hashMap = trieNode.children;
            }
            return validWord;

        }

        return null;

    }

    public String getGoodWordStartingWith(String s) {
        return null;
    }
}
