/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author ThinkPad
 */
public class SearchResultInfo {

    public static final int WORD = 0;
    public static final int PHONETIC_SYMBOL = 1;
    public static final int PARAPHRASE = 2;

    private String inputKeyWord;
    private int resultNumber;
    private ArrayList<OneResult> results;

    public SearchResultInfo(String inputKeyWord) {// develop use only
        this.inputKeyWord = inputKeyWord;
        resultNumber = 50;
        results = new ArrayList<>();

        for (int i = 0; i <= resultNumber; i++) {
            results.add(new OneResult().setWord(inputKeyWord  + i).setPhoneticSymbol("/bʊk/" + i).setParaphrase("n.书，书籍；v.预定" + i));
        }
    }

    public String[] getSearchResultListInfo(int col) {
        String[] s = new String[resultNumber];

        for (int i = 1; i < s.length; i++) {
            if (col == 0) {
                s[i] = String.format("%s", results.get(i).getWord());
            } else if (col == 1) {
                s[i] = String.format("%s", results.get(i).getPhoneticSymbol());
            } else if (col == 2) {
                s[i] = String.format("%s", results.get(i).getParaphrase());
            }
        }

        return s;
    }
}

class OneResult{
    private String word;
    private String phoneticSymbol;
    private String paraphrase;
    
    public OneResult(){
        
    }

    public String getWord() {
        return word;
    }

    public OneResult setWord(String word) {
        this.word = word;
        return this;
    }

    public String getPhoneticSymbol() {
        return phoneticSymbol;
    }

    public OneResult setPhoneticSymbol(String phoneticSymbol) {
        this.phoneticSymbol = phoneticSymbol;
        return this;
    }

    public String getParaphrase() {
        return paraphrase;
    }

    public OneResult setParaphrase(String paraphrase) {
        this.paraphrase = paraphrase;
        return this;
    }
    
    
}
