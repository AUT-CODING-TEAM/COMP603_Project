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
public class MyPlanInfo {

    private int listNumber;
    private ArrayList<StudyPlan> studyPlans;

    public MyPlanInfo() {// develop use only
        listNumber = 7;
        studyPlans = new ArrayList<>();

        for (int i = 0; i < listNumber; i++) {
            StudyPlan studyPlan = new StudyPlan().setStudyPlanName("小学人教版" + i + "年级").setTotalNumber(i + 100).setFinished(i % 2);
            studyPlans.add(studyPlan);
            if (i % 2 == 0) {
                studyPlan.setPlanFinishedDay("2019-10-27");
            }
        }
    }

    public ArrayList<StudyPlan> getStudyPlans() {
        return studyPlans;
    }

    public void setStudyPlans(ArrayList<StudyPlan> studyPlans) {
        this.studyPlans = studyPlans;
    }
}
