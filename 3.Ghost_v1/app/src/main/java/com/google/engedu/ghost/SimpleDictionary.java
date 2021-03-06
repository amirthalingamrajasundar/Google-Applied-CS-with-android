package com.google.engedu.ghost;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SimpleDictionary implements GhostDictionary {
    private ArrayList<String> words;

    public SimpleDictionary(InputStream wordListStream) throws IOException {
        Log.d("test","simple dictionary Constructor");
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        words = new ArrayList<>();
        String line = null;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            if (word.length() >= MIN_WORD_LENGTH)
              words.add(line.trim());
        }
    }

    @Override
    public boolean isWord(String word)
    {
        Log.d("test","simple dictionary isword");

        return words.contains(word);
    }

    @Override
    public String getAnyWordStartingWith(String prefix) {
        Log.d("test","simple dictionary getAnyStartingWith");
        String s;
        s = binarySearch(0,words.size(),prefix);
        
        return s;
    }

    @Override
    public String getGoodWordStartingWith(String prefix) {
        Log.d("test","simple dictionary getgoodwordstartingwith");
        String selected = null;
        return selected;
    }


    public String binarySearch(int first, int last,String prefix){

        if(first>last){
            return null;
        }

        int mid = ((first + last) / 2);
        if(words.get(mid).startsWith(prefix)){
            return words.get(mid);
        }
        else{
            if (prefix.compareTo(words.get(mid))<0){
                return binarySearch(first,mid-1,prefix);
            }
            else{
                return binarySearch(mid+1,last,prefix);
            }
        }

    }
}
