/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Component;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author ThinkPad
 */
public class OnePlanPanel extends JPanel{
    private JLabel lbl_pLP_studyPlanName;
    private JLabel lbl_pLP_totalNumber;
    public OnePlanPanel(){
        setLayout(new GridBagLayout());
    }
    
    public void addStudyPlanName(Component comp, Object constraints) {
        super.add(comp, constraints);
        this.lbl_pLP_studyPlanName = (JLabel)comp;
    }
    
    public void addTotalNumber(Component comp, Object constraints) {
        super.add(comp, constraints);
        this.lbl_pLP_totalNumber = (JLabel)comp;
    }

    public JLabel getLbl_pLP_studyPlanName() {
        return lbl_pLP_studyPlanName;
    }

    public void setLbl_pLP_studyPlanName(JLabel lbl_pLP_studyPlanName) {
        this.lbl_pLP_studyPlanName = lbl_pLP_studyPlanName;
    }

    public JLabel getLbl_pLP_totalNumber() {
        return lbl_pLP_totalNumber;
    }

    public void setLbl_pLP_totalNumber(JLabel lbl_pLP_totalNumber) {
        this.lbl_pLP_totalNumber = lbl_pLP_totalNumber;
    }

}
