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
    private StudyPlan studyPlan;
    private int finishedNumberInPlan;
    private int finishedNumberInTotal;

    public User() {//develop use only
        this.username = "小明";
        this.id = 0;
        this.studyPlan = null;
        this.finishedNumberInPlan = 2;
        this.finishedNumberInTotal = 500;
    }

    public User(String name, String pass, int id) {
        this.username = name;
        this.password = pass;
        this.id = id;
        this.studyPlan = null;
        this.finishedNumberInPlan = 2;
        this.finishedNumberInTotal = 500;
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
        if (this.studyPlan == null) {
            return 0;
        }
        return this.studyPlan.getTodayMemorizedNumber();
    }

    public int getTodayTargetNumber() {
        if (this.studyPlan == null) {
            return 0;
        }
        return this.studyPlan.getTodayTargetNumber();
    }

    public int getTodayReviewNumber() {
        if (this.studyPlan == null) {
            return 0;
        }
        return this.studyPlan.getTodayReviewedNumber();
    }

    public int getRemainingDay() {
        if (this.studyPlan == null) {
            return 0;
        }
        return this.studyPlan.getRemainDay();
    }

    public StudyPlan getStudyPlan() {
        return studyPlan;
    }

    public User setStudyPlan(StudyPlan studyPlan) {
        this.studyPlan = studyPlan;
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
