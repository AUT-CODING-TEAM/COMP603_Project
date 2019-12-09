/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author ThinkPad
 */
public class User {

    private String username;
    private String password;
    private int id;
    private int todayLearnedNumber;
    private int todayTargetNumber;
    private int todayReviewNumber;
    private int remainingDay;
    private StudyPlan currentStudyPlan;
    private int finishedNumberInPlan;
    private int finishedNumberInTotal;

    public User(boolean developMode) {//develop use only
        this.username = "小明";
        this.todayLearnedNumber = 13;
        this.todayTargetNumber = 15;
        this.todayReviewNumber = 2;
        this.remainingDay = 3;
        this.currentStudyPlan = new StudyPlan(developMode);
        this.finishedNumberInPlan = 2;
        this.finishedNumberInTotal = 500;
    }

    public User(String name, String pass, int id) {
        this.username = name;
        this.password = pass;
        this.id = id;
    }
    
    public User(String name, int total_num){
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
    
    public User setTodayLearnedNumber(int todayLearnedNumber) {
        this.todayLearnedNumber = todayLearnedNumber;
        return this;
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

    public int getFinishedNumberInPlan() {
        return finishedNumberInPlan;
    }
    
    public User setFinishedNumberInPlan(int finishedNumberInPlan) {
        this.finishedNumberInPlan = finishedNumberInPlan;
        return this;
    }

}
