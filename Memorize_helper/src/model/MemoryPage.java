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

    private int studyNumber;//maybe new learn, review or favorite
    private Word wordObj;
    private String word;
    private String phoneticSymbol;
    private Set<Word> choices;
    private String source;//maybe new learn, review or favorite

    public MemoryPage() {

    }

    public String getSource() {
        return source;
    }

    public MemoryPage setSource(String source) {
        this.source = source;
        return this;
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

    public int getStudyNumber() {
        return studyNumber;
    }

    public MemoryPage setLearnNumber(int studyNumber) {
        this.studyNumber = studyNumber;
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
