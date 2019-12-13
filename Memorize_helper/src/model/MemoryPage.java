/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Set;

/**
 *
 * @author ThinkPad
 */
public class MemoryPage {

    private int learnNumber;
    private int reviewNumber;
    private Word wordObj;
    private String word;
    private String phoneticSymbol;
    private Set<Word> choices;

    public MemoryPage() {

    }

    public MemoryPage(boolean developMode) {//develop use only
        this.learnNumber = 15;
        this.reviewNumber = 60;
//        this.word = "book";
        this.phoneticSymbol = "/bʊk/";
//        this.choices = new String[]{"n.老虎", "n.书，书籍；v.预定", "num.九；九个", "n.铅笔"};
    }

    public Word getWordObj() {
        return wordObj;
    }

    public MemoryPage setWordObj(Word wordObj) {
        this.wordObj = wordObj;
        setWord();
        setPhoneticSymbol();
        return this;
    }

    public int getLearnNumber() {
        return learnNumber;
    }

    public MemoryPage setLearnNumber(int learnNumber) {
        this.learnNumber = learnNumber;
        return this;
    }

    public int getReviewNumber() {
        return reviewNumber;
    }

    public MemoryPage setReviewNumber(int reviewNumber) {
        this.reviewNumber = reviewNumber;
        return this;
    }

    public String getWord() {
        return word;
    }

    private MemoryPage setWord() {
        this.word = wordObj.getWord();
        
        return this;
    }

    public String getPhoneticSymbol() {
        return phoneticSymbol;
    }

    private MemoryPage setPhoneticSymbol() {
        this.phoneticSymbol = wordObj.getPhonetic();
        return this;
    }

    public Set<Word> getChoices() {
        return choices;
    }

    public MemoryPage setChoices(Set<Word> choices) {
        this.choices = choices;
        return this;
    }
}
