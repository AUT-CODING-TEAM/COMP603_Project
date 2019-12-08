/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.myPlan;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import model.User;
import view.main.MainView;
import view.myPlan.MyPlanPanel;
import view.planList.CreatePlanPanel;

/**
 *
 * @author ThinkPad
 */
public class MakePlanController implements ActionListener{
    private User user;
    private CreatePlanPanel createPlanPanel;
    
    public MakePlanController(User user, CreatePlanPanel createPlanPanel){
        this.user = user;
        this.createPlanPanel = createPlanPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (createPlanPanel.getQuantity() != null) {
            System.out.println(createPlanPanel.getQuantity());
            System.out.println(createPlanPanel.getSelectedPlan().getStudyPlanName());
//            new MainView(user);
        }
        
    }
    
}
