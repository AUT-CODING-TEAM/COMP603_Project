/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.myPlan;

import controller.interfaces.UserController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
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
    private JFrame selectedPlanFrame;
    
    public MakePlanController(User user, CreatePlanPanel createPlanPanel, JFrame selectedPlanFrame){
        this.user = user;
        this.createPlanPanel = createPlanPanel;
        this.selectedPlanFrame = selectedPlanFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (createPlanPanel.getQuantity() != null) {
            System.out.println(createPlanPanel.getQuantity());
            System.out.println(createPlanPanel.getSelectedPlan().getStudyPlanName());
            
            if (createPlanPanel.getQuantity().contains("天")) {
                Integer.parseInt(createPlanPanel.getQuantity().split("天")[0]);
                new UserController().activateStudyPlanByDay(user, createPlanPanel.getSelectedPlan().getStudyPlanName(), Integer.parseInt(createPlanPanel.getQuantity().split("天")[0]));
            }
            else{
                System.out.println(Integer.parseInt(createPlanPanel.getQuantity().split("词")[0]));
                new UserController().activateStudyPlanByNum(user, createPlanPanel.getSelectedPlan().getStudyPlanName(), Integer.parseInt(createPlanPanel.getQuantity().split("词")[0]));
            }
            selectedPlanFrame.dispose();
            new MainView(user);
        }
    }
}
