/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.planList;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import model.User;
import view.OnePlanPanel;
import view.planList.CreatePlanPanel;

/**
 *
 * @author ThinkPad
 */
public class AddPlanController implements MouseListener{
    private User user;
    private JFrame planListFrame;
    
    public AddPlanController(User user, JFrame planListFrame){
        this.user = user;
        this.planListFrame = planListFrame;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        OnePlanPanel jPanel = (OnePlanPanel)e.getSource();
        System.out.println(jPanel.getLbl_pLP_studyPlanName().getText());
        new CreatePlanPanel(user, jPanel.getStudyPlan());
        planListFrame.dispose();
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
}
