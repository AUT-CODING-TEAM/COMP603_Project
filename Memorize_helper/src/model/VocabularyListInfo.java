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
public class VocabularyListInfo {

    public static final int STUDIED = 0;
    public static final int UNSTUDIED = 1;
    public static final int CUT = 2;
    public static final int COLLECT = 3;

    private ArrayList<String> words;
    private ArrayList<String> chinese;

    public VocabularyListInfo(int option) {// develop use only
        int listNumber = 50;
        this.words = new ArrayList<>();
        this.chinese = new ArrayList<>();

        for (int i = 0; i < listNumber; i++) {
            String word = "pencil" + i;
            String chinese = "n.铅笔" + i;

            if (option == 0) {
                word += "0";
                chinese += "0";
            } else if (option == 1) {
                word += "1";
                chinese += "1";
            } else if (option == 2) {
                word += "2";
                chinese += "2";
            } else if (option == 3) {
                word += "3";
                chinese += "3";
            }
            this.words.add(word);
            this.chinese.add(chinese);
        }
    }

    public String[] getVocabularyListInfo(int col) {
        String[] s = new String[words.size()];

        for (int i = 0; i < s.length; i++) {
            if (col == 0) {
                s[i] = String.format("%15s", words.get(i));
            } else if (col == 1) {
                s[i] = String.format("%s", chinese.get(i));
            }
        }
        
        return s;
    }
}
