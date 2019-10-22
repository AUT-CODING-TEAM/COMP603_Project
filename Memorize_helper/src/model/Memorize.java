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
public class Memorize {

    int id;
    int word_id;
    int user_id;
    String word_source;
    int correct;
    int wrong;
    long time;
    int age;

    public Memorize(int id, int word_id, int user_id, String word_source,
            int correct, int wrong, long time, int age) {
        this.id = id;
        this.word_id = word_id;
        this.user_id = user_id;
        this.word_source = word_source;
        this.correct = correct;
        this.wrong = wrong;
        this.time = time;
        this.age = age;
    }

    public int getID() {
        return this.id;
    }

    public int getWordID() {
        return this.word_id;
    }

    public int getUserID() {
        return this.user_id;
    }

    public String getWordSource() {
        return this.word_source;
    }

    public int getCorrect() {
        return this.correct;
    }

    public int getWrong() {
        return this.wrong;
    }

    public long getTime() {
        return this.time;
    }

    public int getAge() {
        return this.age;
    }

    public void addCorrect() {
        this.correct += 1;
    }

    public void addWrong() {
        this.wrong += 1;
    }
}
