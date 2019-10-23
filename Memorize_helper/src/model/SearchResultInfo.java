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

    private String inputKeyWord;
    private int resultNumber;//develop use only
    private ArrayList<String> results;

    public SearchResultInfo(String inputKeyWord) {// develop use only
        this.inputKeyWord = inputKeyWord;
        resultNumber = 50;
        results = new ArrayList<>();

        for (int i = 0; i <= resultNumber; i++) {
            results.add(inputKeyWord + i);
        }
    }

    public String[] getSearchResultListInfo() {
        String[] s = new String[results.size()];

        for (int i = 1; i < s.length; i++) {
            s[i] = String.format("%s", results.get(i));
        }

        return s;
    }
}
