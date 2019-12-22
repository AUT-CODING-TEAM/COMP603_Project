/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.myPlan;

import controller.interfaces.PlanController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.User;

/**
 *
 * @author pingchuan.huang
 *
 * This listener controls user
 */
public class PlanManagementListener implements ActionListener {

    private User user;
    private String plan;

    public PlanManagementListener(User user) {
        this.user = user;
        this.plan = plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        

    }

}
