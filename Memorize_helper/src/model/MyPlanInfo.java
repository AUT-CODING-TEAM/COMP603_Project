/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.interfaces.UserController;
import java.util.ArrayList;

/**
 *
 * @author ThinkPad
 */
public class MyPlanInfo {
    private ArrayList<StudyPlan> myStudyPlans;
    
    public MyPlanInfo(User user){
       myStudyPlans = new UserController().bookPlanList(user);
    }

    public ArrayList<StudyPlan> getMyStudyPlans() {
        return myStudyPlans;
    }

    public void setMyStudyPlans(ArrayList<StudyPlan> myStudyPlans) {
        this.myStudyPlans = myStudyPlans;
    }
}
