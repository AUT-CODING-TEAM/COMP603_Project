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
    private ArrayList<Word> wordsToStudy;//maybe new learn, review or favorite
    private User user;
    private int process;
    private String source;//maybe new learn, review or favorite

    public MemoryRecorder(User user, ArrayList<Word> wordsToStudy, String source) {
        this.user = user;
        this.wordsToStudy = wordsToStudy;
        this.source = source;
        this.process = 0;
    }
    
    public int getWordsToStudySize(){
        return wordsToStudy.size();
    }
    
    public MemoryPage next() {
        if (process == wordsToStudy.size()) {
            return null;
        }
        
        Word nextWord = wordsToStudy.get(process);
        MemoryPage memoryPage = null;
        try {
            memoryPage = new MemoryPage()
                    .setSource(source)
                    .setLearnNumber(wordsToStudy.size() - process)
                    .setWordObj(nextWord)
                    .setChoices(new MemorizeController().getOptions(user.getCurrentStudyPlan().getStudyPlanName(), nextWord));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        process++;
        return memoryPage;
    }

    public ArrayList<Word> getWordsToStudy() {
        return wordsToStudy;
    }

    public String getSource() {
        return source;
    }
    
    public int getProcess() {
        return process;
    }

    public void setProcess(int process) {
        this.process = process;
    }
}
