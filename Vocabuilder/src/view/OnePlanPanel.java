package view;

import java.awt.Component;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.StudyPlan;

/**
 *
 * @author ThinkPad
 */
public class OnePlanPanel extends JPanel{
    private StudyPlan studyPlan;
    private JLabel planNameLabel;
    private JLabel totalNumLabel;
    public OnePlanPanel(){
        setLayout(new GridBagLayout());
    }

    public StudyPlan getStudyPlan() {
        return studyPlan;
    }

    public void setStudyPlan(StudyPlan studyPlan) {
        this.studyPlan = studyPlan;
    }
    
    public void addStudyPlanName(Component comp, Object constraints) {
        super.add(comp, constraints);
        this.planNameLabel = (JLabel)comp;
    }
    
    public void addTotalNumber(Component comp, Object constraints) {
        super.add(comp, constraints);
        this.totalNumLabel = (JLabel)comp;
    }

    public JLabel getPlanNameLabel() {
        return planNameLabel;
    }

    public void setPlanNameLabel(JLabel planNameLabel) {
        this.planNameLabel = planNameLabel;
    }

    public JLabel getTotalNumLabel() {
        return totalNumLabel;
    }

    public void setTotalNumLabel(JLabel totalNumLabel) {
        this.totalNumLabel = totalNumLabel;
    }

}
