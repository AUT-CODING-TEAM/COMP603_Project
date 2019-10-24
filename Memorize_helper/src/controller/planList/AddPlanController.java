/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.planList;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
import view.planList.OnePlanPanel;

/**
 *
 * @author ThinkPad
 */
public class AddPlanController implements MouseListener{

    @Override
    public void mouseClicked(MouseEvent e) {
        OnePlanPanel jPanel = (OnePlanPanel)e.getSource();
        System.out.println(jPanel.getLbl_pLP_studyPlanName().getText());
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
