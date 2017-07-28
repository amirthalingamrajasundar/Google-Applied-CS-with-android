package com.google.engedu.anagrams;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    private HashSet<String> wordSet = new HashSet<>();
    private ArrayList<String> wordList = new ArrayList<>();
    private HashMap<String, ArrayList> lettersToWords = new HashMap<>();
    private HashMap<String, ArrayList> sizeToWords = new HashMap<>();

    public AnagramDictionary(InputStream wordListStream) throws IOException {
        Log.d("test","constructor dictonary");
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordSet.add(word);
            wordList.add(word);
            if(lettersToWords.containsKey(alphaOrder(word))){

                lettersToWords.get(alphaOrder(word)).add(word);

            }else{

                ArrayList<String> temp = new ArrayList<>();
                temp.add(word);
                lettersToWords.put(alphaOrder(word),temp);

            }
        }
    }

    public String alphaOrder(String toSort){
        Log.d("test","alphaOrder dictonary");
        char[] sorted = toSort.toCharArray();
        Arrays.sort(sorted);
        return new String(sorted);

    }



    public boolean isGoodWord(String word, String base) {
        Log.d("test","isGoodWord dictonary");
        if(wordSet.contains(word)){

            if(!word.contains(base))

                return true;

        }

        return false;

    }

    public ArrayList<String> getAnagrams(String targetWord) {
        Log.d("test","getAnagram dictonary");

        ArrayList<String> result = new ArrayList<String>();

        if(wordSet.contains(targetWord)){

            result = lettersToWords.get(alphaOrder(targetWord));
            result.remove(targetWord);
        }

        return result;
    }

    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        return result;
    }

    public String pickGoodStarterWord() {
        Log.d("test","pickgoodStarterWord dictonary");
        int size = 0;
        for(String sorted : lettersToWords.keySet()){
            size = lettersToWords.get(sorted).size();
            if(size >= MIN_NUM_ANAGRAMS){
                return (String)lettersToWords.get(sorted).get(random.nextInt(size));
            }
        }

        return null;
    }
}
