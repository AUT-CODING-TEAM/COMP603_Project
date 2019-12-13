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
    private int process;
    private User user;
    
    public MemoryRecorder(User user){
        this.user = user;
        process = 0;
        try {
            wordsToLearn = new MemorizeController().getWordList(user);
            wordsToReview = new MemorizeController().getReviewWordLists(user);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public MemoryPage next() {
        if (process == wordsToLearn.size() + wordsToReview.size()) {
            return null;
        }
        
        Word nextWord = wordsToLearn.size() > process ? wordsToLearn.get(process) : wordsToReview.get(process - wordsToLearn.size());
        MemoryPage memoryPage = null;
        try {
            memoryPage = new MemoryPage()
                    .setLearnNumber(wordsToLearn.size() > process ? wordsToLearn.size() - process : 0)
                    .setReviewNumber(wordsToLearn.size() > process ? wordsToReview.size() : wordsToReview.size() - (process - wordsToLearn.size()))
                    .setWordObj(nextWord)
                    .setChoices(new MemorizeController().getOptions(user.getCurrentStudyPlan().getStudyPlanName(), nextWord));

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        process++;
        return memoryPage;
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
