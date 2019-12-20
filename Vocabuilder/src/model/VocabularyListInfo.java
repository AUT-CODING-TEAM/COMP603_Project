/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.interfaces.CollectionController;
import controller.interfaces.MemorizeController;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ThinkPad
 */
public class VocabularyListInfo {

    public static final int STUDIED = 0;
    public static final int ALL_WORDS = 1;
    public static final int COLLECT = 2;

    private ArrayList<String> words;
    private ArrayList<String> chinese;

    public VocabularyListInfo(int option, User user) {
        this.words = new ArrayList<>();
        this.chinese = new ArrayList<>();
        ArrayList<Word> wordListInDB = null;

        try{
            if (option == 0) {
                wordListInDB = new MemorizeController().getLearntWords(user);
            } else if (option == 1) {
                wordListInDB = new MemorizeController().getPlanWords(user);
            } else if (option == 2) {
                wordListInDB = new CollectionController().getCollectedWords(user);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
        for (int i = 0; i < wordListInDB.size(); i++) {
            this.words.add(wordListInDB.get(i).getWord());
            this.chinese.add(wordListInDB.get(i).getChinese());
        }
    }
    
    public String[] getVocabularyListInfo(){
        String[] s = new String[words.size()];
        for (int i = 0; i < s.length; i++) {
            s[i] = String.format("%15s %25s", words.get(i), chinese.get(i));
        }
        return s;
    }
}
