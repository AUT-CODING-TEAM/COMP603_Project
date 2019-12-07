/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.interfaces.WordController;
import java.util.ArrayList;

/**
 *
 * @author ThinkPad
 */
public class PlanListInfo {

    private int listNumber;
    private ArrayList<StudyPlan> studyPlans;

    public PlanListInfo(boolean developMode) {// develop use only
        listNumber = 52;
        studyPlans = new ArrayList<>();

        for (int i = 0; i < listNumber; i++) {
            studyPlans.add(new StudyPlan(developMode).setStudyPlanName("小学人教版" + i + "年级").setTotalNumber(i).setAdded(i % 2));
        }
    }
    
    public PlanListInfo(){
//        ArrayList booksNameInDB = new WordController().getAllBook();
//        
//        listNumber = booksNameInDB.size();
        
    }

    public ArrayList<StudyPlan> getStudyPlans() {
        return studyPlans;
    }

    public void setStudyPlans(ArrayList<StudyPlan> studyPlans) {
        this.studyPlans = studyPlans;
    }

}
