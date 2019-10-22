/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Yun_c
 */
public class Word {

    private int id;
    private String word;
    private String chinese;
    private String phonetic;
    private String source;

    public Word() {
        this.id = 0;
        this.word = "";
        this.chinese = "";
        this.phonetic = "";
        this.source = "";
    }

    public Word(int id, String word, String chinese,
            String phonetic, String source) {
        this.id = id;
        this.word = word;
        this.chinese = chinese;
        this.phonetic = phonetic;
        this.source = source;
    }
    
    public int getID(){
        return this.id;
    }
    public String getWord(){
        return this.word;
    }
    public String getChinese(){
        return this.chinese;
    }
    public String getPhonetic(){
        return this.phonetic;
    }
    public String getSource(){
        return this.source;
    }
}
