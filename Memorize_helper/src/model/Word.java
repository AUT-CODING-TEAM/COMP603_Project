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

    public int getID() {
        return this.id;
    }

    public String getWord() {
        return this.word;
    }

    public String getChinese() {
        return this.chinese;
    }

    public String getPhonetic() {
        return this.phonetic;
    }

    public String getSource() {
        return this.source;
    }

    @Override
    public String toString() {
        StringBuilder bd = new StringBuilder();
        bd.append("ID:\t\t").append(this.id).append("\n");
        bd.append("from:\t\t").append(this.source).append("\n");
        bd.append("word:\t\t").append(this.word).append("\n");
        bd.append("chinese:\t").append(this.chinese).append("\n");
        bd.append("phonetic:\t").append(this.phonetic).append("\n");
        return bd.toString();
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Word)) {
            return false;
        }
        
        Word w = (Word) obj;
        return this.id == w.id
                && this.source.equals(w.getSource());

    }

    @Override
    public int hashCode() {
        StringBuilder sb = new StringBuilder();
        sb.append(id)
                .append(word)
                .append(chinese)
                .append(phonetic)
                .append(source);
        return sb.toString().hashCode();
    }

}
