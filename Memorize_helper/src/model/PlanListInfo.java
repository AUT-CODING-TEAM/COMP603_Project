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
public class PlanListInfo {
    private ArrayList<StudyPlan> studyPlans;
    
    public PlanListInfo(User user){
        studyPlans = new ArrayList<>();
        
        Map<String, String> booksInDB = new UserController().AllBookInfo(user);
        for (Map.Entry<String, String> entry : booksInDB.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            
            int added = value.split(",")[1].equals("true")? 1 : 0;
            studyPlans.add(new StudyPlan(key, Integer.parseInt(value.split(",")[0]), added));
        }
        
        //fill
        int studyPlansSize = studyPlans.size();
        for (int i = 1; i + studyPlansSize < 6; i++) {
            studyPlans.add(new StudyPlan("fill", -1, 0));
        }
    }

    public ArrayList<StudyPlan> getStudyPlans() {
        return studyPlans;
    }

    public void setStudyPlans(ArrayList<StudyPlan> studyPlans) {
        this.studyPlans = studyPlans;
    }

}
