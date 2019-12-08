/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.interfaces.UserController;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author ThinkPad
 */
public class MyPlanInfo {

    private int listNumber;
    private ArrayList<StudyPlan> myStudyPlans;
    
    public MyPlanInfo(User user){
       myStudyPlans = new UserController().bookPlanList(user);
    }

    public MyPlanInfo(boolean developMode) {// develop use only
        listNumber = 7;
        myStudyPlans = new ArrayList<>();

        for (int i = 0; i < listNumber; i++) {
            StudyPlan studyPlan = new StudyPlan(developMode).setStudyPlanName("小学人教版" + i + "年级").setTotalNumber(i + 100).setFinished(i % 2);
            myStudyPlans.add(studyPlan);
            if (i % 2 == 0) {
                studyPlan.setPlanFinishedDay("2019-10-27");
            }
        }
    }

    public ArrayList<StudyPlan> getMyStudyPlans() {
        return myStudyPlans;
    }

    public void setMyStudyPlans(ArrayList<StudyPlan> myStudyPlans) {
        this.myStudyPlans = myStudyPlans;
    }
}
