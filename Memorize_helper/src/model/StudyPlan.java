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
public class StudyPlan {
    private String studyPlanName;
    private int totalNumber;
    private int added;//is added by the user or not
    
    public StudyPlan() {//develop use only
        this.studyPlanName = "小学人教版一年级上";
        this.totalNumber = 35;
    }

    public int getAdded() {
        return added;
    }

    public StudyPlan setAdded(int added) {
        this.added = added;
        return this;
    }

    public String getStudyPlanName() {
        return studyPlanName;
    }

    public StudyPlan setStudyPlanName(String studyPlanName) {
        this.studyPlanName = studyPlanName;
        return this;
    }

    public int getTotalNumber() {
        return totalNumber;
    }

    public StudyPlan setTotalNumber(int totalNumber) {
        this.totalNumber = totalNumber;
        return this;
    }
}
