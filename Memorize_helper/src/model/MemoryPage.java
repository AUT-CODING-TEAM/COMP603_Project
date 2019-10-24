/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author ThinkPad
 */
public class MemoryPage {
    private int targetNumber;
    private int turnNumer;
    private String word;
    private String phoneticSymbol;
    private String[] choices;
    
    public MemoryPage(){//develop use only
        this.targetNumber = 15;
        this.turnNumer = 60;
        this.word = "book";
        this.phoneticSymbol = "/bʊk/";
        this.choices = new String[]{"n.老虎", "n.书，书籍；v.预定", "num.九；九个", "n.铅笔"};
    }

    public int getTargetNumber() {
        return targetNumber;
    }

    public void setTargetNumber(int targetNumber) {
        this.targetNumber = targetNumber;
    }

    public int getTurnNumer() {
        return turnNumer;
    }

    public void setTurnNumer(int turnNumer) {
        this.turnNumer = turnNumer;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getPhoneticSymbol() {
        return phoneticSymbol;
    }

    public void setPhoneticSymbol(String phoneticSymbol) {
        this.phoneticSymbol = phoneticSymbol;
    }

    public String[] getChoices() {
        return choices;
    }

    public void setChoices(String[] choices) {
        this.choices = choices;
    }
    
    
}
