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
public class User {

    private String username;
    private String password;
    private int id;
    private StudyPlan currentStudyPlan;
    private int finishedNumberInTotal;

    public User(String name, String pass, int id) {
        this.username = name;
        this.password = pass;
        this.id = id;
    }

    public User(String name, int total_num) {
        this.username = name;
        this.finishedNumberInTotal = total_num;
    }

    public String getSeqPassword() {
        return this.password;
    }

    public String getUsername() {
        return username;
    }

    public int getID() {
        return this.id;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public int getFinishedNumberInTotal() {
        return finishedNumberInTotal;
    }

    public User setFinishedNumberInTotal(int finishedNumberInTotal) {
        this.finishedNumberInTotal = finishedNumberInTotal;
        return this;
    }

    public int getTodayLearnedNumber() {
        if (this.currentStudyPlan == null) {
            return 0;
        }
        return this.currentStudyPlan.getTodayMemorizedNumber();
    }

    public int getTodayTargetNumber() {
        if (this.currentStudyPlan == null) {
            return 0;
        }
        return this.currentStudyPlan.getTodayTargetNumber();
    }

    public int getTodayReviewNumber() {
        
        if (this.currentStudyPlan == null) {
            return 0;
        }

        return this.currentStudyPlan.getNeedReviewNumber();
    }

    public int getTodayReviewedNumber() {
        if (this.currentStudyPlan == null) {
            return 0;
        }
        return this.currentStudyPlan.getTodayReviewedNumber();
    }

    public int getRemainingDay() {
        if (this.currentStudyPlan == null) {
            return 0;
        }
        return this.currentStudyPlan.getRemainDay();
    }

    public StudyPlan getCurrentStudyPlan() {
        return currentStudyPlan;
    }

    public User setCurrentStudyPlan(StudyPlan currentStudyPlan) {
        this.currentStudyPlan = currentStudyPlan;
        return this;
    }

    

}
