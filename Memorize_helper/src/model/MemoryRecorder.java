/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.interfaces.MemorizeController;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ThinkPad
 */
public class MemoryRecorder {
    private ArrayList<Word> wordsToLearn;
    private ArrayList<Word> wordsToReview;
    private ArrayList<Word> wordsToReviewFromPromOrErr;
    private int process;
    private User user;
    
    public MemoryRecorder(User user){
        this.user = user;
        process = 0;
        try {
            wordsToLearn = new MemorizeController().getWordList(user);
            wordsToReview = new ArrayList<>();
            wordsToReviewFromPromOrErr = new ArrayList<>();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public MemoryPage next() {
        if (wordsToLearn.size() > process) {
            try {
                wordsToReview = new MemorizeController().getReviewWordLists(user);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        
        if (process == wordsToLearn.size() + wordsToReview.size() + wordsToReviewFromPromOrErr.size()) {
            return null;
        }
        
        Word nextWord;
        if (wordsToLearn.size() > process) {
            System.out.println("learn new");
            nextWord = wordsToLearn.get(process);
        }
        else if (wordsToLearn.size() +  wordsToReview.size()> process) {
            System.out.println("review");
            nextWord = wordsToReview.get(process - wordsToLearn.size());
        }
        else{
            System.out.println("prom or err");
            nextWord = wordsToReviewFromPromOrErr.get(process - wordsToLearn.size() - wordsToReview.size());
        }
        
        MemoryPage memoryPage = null;
        try {
            System.out.println("learn: " + wordsToLearn.size() + " review: " + wordsToReview.size() + " PromOrErr: " + wordsToReviewFromPromOrErr.size() + " process: " + process + " word: " + nextWord.getWord() + "\n");
            memoryPage = new MemoryPage()
                    .setLearnNumber(wordsToLearn.size() > process ? wordsToLearn.size() - process : 0)
                    .setReviewNumber(wordsToLearn.size() > process ? wordsToReview.size() + wordsToReviewFromPromOrErr.size() : wordsToReview.size() + wordsToReviewFromPromOrErr.size() - (process - wordsToLearn.size()))
                    .setWordObj(nextWord)
                    .setChoices(new MemorizeController().getOptions(user.getCurrentStudyPlan().getStudyPlanName(), nextWord));

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        process++;
        return memoryPage;
    }

    public ArrayList<Word> getWordsToReviewFromPromOrErr() {
        return wordsToReviewFromPromOrErr;
    }

    public ArrayList<Word> getWordsToLearn() {
        return wordsToLearn;
    }

    public ArrayList<Word> getWordsToReview() {
        return wordsToReview;
    }

    public void setWordsToReview(ArrayList<Word> wordsToReview) {
        this.wordsToReview = wordsToReview;
    }

    public int getProcess() {
        return process;
    }

    public void setProcess(int process) {
        this.process = process;
    }
}
