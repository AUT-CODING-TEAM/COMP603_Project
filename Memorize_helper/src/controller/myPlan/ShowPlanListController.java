/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.myPlan;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import model.PlanListInfo;
import model.User;
import view.planList.PlanListPanel;

/**
 *
 * @author ThinkPad
 */
public class ShowPlanListController implements ActionListener {

    private JFrame myPlanFrame;
    private User user;

    public ShowPlanListController(JFrame myPlanFrame, User user) {
        this.myPlanFrame = myPlanFrame;
        this.user = user;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("查看所有计划JFrame");
        new PlanListPanel(user, new PlanListInfo());
        myPlanFrame.dispose();
    }
}
