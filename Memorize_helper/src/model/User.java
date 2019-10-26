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
    private int todayLearnedNumber;
    private int todayTargetNumber;
    private int todayReviewNumber;
    private int remainingDay;
    private StudyPlan studyPlan;
    private int finishedNumberInPlan;
    private int finishedNumberInTotal;

    public User() {//develop use only
        this.username = "小明";
        this.todayLearnedNumber = 13;
        this.todayTargetNumber = 15;
        this.todayReviewNumber = 2;
        this.remainingDay = 3;
        this.studyPlan = new StudyPlan();
        this.finishedNumberInPlan = 2;
        this.finishedNumberInTotal = 500;
    }

    public int getFinishedNumberInTotal() {
        return finishedNumberInTotal;
    }

    public User setFinishedNumberInTotal(int finishedNumberInTotal) {
        this.finishedNumberInTotal = finishedNumberInTotal;
        return this;
    }

    public String getUsername() {
        return username;
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

    public int getFinishedNumberInPlan() {
        return finishedNumberInPlan;
    }

    public User setFinishedNumberInPlan(int finishedNumberInPlan) {
        this.finishedNumberInPlan = finishedNumberInPlan;
        return this;
    }

}
