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
public class WordExplainPage {

    private String word;
    private String phoneticSymbol;
    private String Chinese;
    
    public WordExplainPage(){//develop use only
        this.word = "book";
        this.phoneticSymbol = "/bʊk/";
        this.Chinese = "n.书，书籍；v.预定";
    }

    public String getWord() {
        return word;
    }

    public WordExplainPage setWord(String word) {
        this.word = word;
        return this;
    }

    public String getPhoneticSymbol() {
        return phoneticSymbol;
    }

    public WordExplainPage setPhoneticSymbol(String phoneticSymbol) {
        this.phoneticSymbol = phoneticSymbol;
        return this;
    }

    public String getChinese() {
        return Chinese;
    }

    public WordExplainPage setChinese(String Chinese) {
        this.Chinese = Chinese;
        return this;
    }
    
    
}
