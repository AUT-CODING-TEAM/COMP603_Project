/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.interfaces.WordController;
import java.util.ArrayList;

/**
 *
 * @author ThinkPad
 */
public class SearchResultInfo {

    private String inputKeyWord;
    private ArrayList<String> results;
    
    public SearchResultInfo(String inputKeyWord) {// develop use only
        this.inputKeyWord = inputKeyWord;
        results = new ArrayList<>();
        ArrayList<Word> resultsInDB = new WordController().search(inputKeyWord);

        for (int i = 0; i < resultsInDB.size(); i++) {
            results.add(resultsInDB.get(i).getWord());
        }
    }

    public String[] getSearchResultListInfo() {
        String[] s = new String[results.size()];

        for (int i = 0; i < s.length; i++) {
            s[i] = String.format("%50s", results.get(i));
        }

        return s;
    }
}
