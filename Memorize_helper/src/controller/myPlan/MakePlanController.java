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

/**
 *
 * @author ThinkPad
 */
public class MakePlanController implements ActionListener{
    private User user;
    private MyPlanPanel myPlanPanel;
    
    public MakePlanController(User user, MyPlanPanel myPlanPanel){
        this.user = user;
        this.myPlanPanel = myPlanPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (myPlanPanel.getBookName() != null && myPlanPanel.getQuantity() != null) {
            System.out.println(myPlanPanel.getBookName());
            System.out.println(myPlanPanel.getQuantity());
            myPlanPanel.getMyPlanFrame().dispose();
            new MainView(user);
        }
    }
    
}
