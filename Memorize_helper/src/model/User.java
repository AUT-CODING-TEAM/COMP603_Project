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
    private int todayLearnedNumber;
    private int todayTargetNumber;
    private int todayReviewNumber;
    private int remainingDay;
    private StudyPlan studyPlan;
    private int finishedNumber;
    
    public User(){//develop use only
        this.username = "小明";
        this.id = 0;
        this.todayLearnedNumber = 13;
        this.todayTargetNumber = 15;
        this.todayReviewNumber = 2;
        this.remainingDay = 3;
        this.studyPlan = new StudyPlan();
        this.finishedNumber = 2;
    }
    
    public User(String name, String pass,int id){
        this.username = name;
        this.password = pass;
        this.id = id;
        this.todayLearnedNumber = 13;
        this.todayTargetNumber = 15;
        this.todayReviewNumber = 2;
        this.remainingDay = 3;
        this.studyPlan = new StudyPlan();
        this.finishedNumber = 2;
    }
    public String getSeqPassword(){
        return this.password;
    }
    public String getUsername() {
        return username;
    }
    public int getID(){
        return this.id;
    }
    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public int getTodayLearnedNumber() {
        return todayLearnedNumber;
    }

    public User setTodayLearnedNumber(int todayLearnedNumber) {
        this.todayLearnedNumber = todayLearnedNumber;
        return this;
    }

    public int getTodayTargetNumber() {
        return todayTargetNumber;
    }

    public User setTodayTargetNumber(int todayTargetNumber) {
        this.todayTargetNumber = todayTargetNumber;
        return this;
    }

    public int getTodayReviewNumber() {
        return todayReviewNumber;
    }

    public User setTodayReviewNumber(int todayReviewNumber) {
        this.todayReviewNumber = todayReviewNumber;
        return this;
    }

    public int getRemainingDay() {
        return remainingDay;
    }

    public User setRemainingDay(int remainingDay) {
        this.remainingDay = remainingDay;
        return this;
    }

    public StudyPlan getStudyPlan() {
        return studyPlan;
    }

    public User setStudyPlan(StudyPlan studyPlan) {
        this.studyPlan = studyPlan;
        return this;
    }

    public int getFinishedNumber() {
        return finishedNumber;
    }

    public User setFinishedNumber(int finishedNumber) {
        this.finishedNumber = finishedNumber;
        return this;
    }

    
}
