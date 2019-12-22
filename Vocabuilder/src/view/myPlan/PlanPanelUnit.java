package view.myPlan;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import model.StudyPlan;
import model.User;
import view.GridBagTool;

/**
 *
 * @author Yun_c
 */
public class PlanPanelUnit extends JPanel implements MouseListener {

    public static Map<String, Border> BorderTable = new HashMap<String, Border>() {
        {
            put("COMPLETED", new TitledBorder("Completed"));
            put("NONE", null);
            put("SELECTED", BorderFactory.createLineBorder(new Color(45, 140, 240), 5));
        }
    };

    public static Map<String, Color> ColorTable = new HashMap<String, Color>() {
        {
            put("ACTIVATED", new Color(23, 35, 61));
            put("NO_ACTIVATED", new Color(91, 110, 125));
        }
    };

    private JLabel planName;
    private JLabel wordNumber;
    private StudyPlan plan;
    private boolean selected;
    private boolean activated;

    public PlanPanelUnit(User u, StudyPlan p) {
        this.planName = new JLabel(p.getStudyPlanName(), SwingConstants.CENTER);
        this.planName.setFont(new Font("FACE_SYSTEM", Font.PLAIN, 20));
        this.wordNumber = new JLabel(String.valueOf(p.getTotalNumber()) + "words", SwingConstants.CENTER);
        this.wordNumber.setFont(new Font("FACE_SYSTEM", Font.PLAIN, 15));

        this.setLayout(new GridBagLayout());
        this.add(this.planName, new GridBagTool().setGridx(0).setGridy(0).setGridwidth(1).setGridheight(1).setWeightx(1).setWeighty(0.8));
        this.add(this.wordNumber, new GridBagTool().setGridx(0).setGridy(1).setGridwidth(1).setGridheight(1).setWeightx(1).setWeighty(0.2));

        this.setBackground(PlanPanelUnit.ColorTable.get("NO_ACTIVATED"));
        if (u.getCurrentStudyPlan().getStudyPlanName().equals(this.planName.getText())) {
            this.setActivated();
        }
        this.plan = p;
        this.addMouseListener(this);
    }

    private void setActivated() {
        this.setBackground(PlanPanelUnit.ColorTable.get("ACTIVATED"));
        StringBuilder sb = new StringBuilder();
        sb.append("<html>&nbsp;(Current)<br>");
        sb.append(this.wordNumber.getText()).append("</html>");
        this.wordNumber.setText(sb.toString());
        this.wordNumber.setForeground(Color.white);
        this.planName.setForeground(Color.white);
        this.activated = true;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public boolean isActivated() {
        return this.activated;
    }
    
    public void activate(){
        this.activated = true;
        this.setForeground(ColorTable.get("ACTIVATED"));
    }

    public void setSelect() {
        if (plan.getFinished() == 0) {
            this.setBorder(PlanPanelUnit.BorderTable.get("SELECTED"));
            this.selected = true;
        }
    }

    public void unSelect() {
        if (plan.getFinished() == 0) {
            this.setBorder(PlanPanelUnit.BorderTable.get("NONE"));
            this.selected = false;
        }
    }

    public String getPlanName() {
        return this.planName.getText();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        this.setSelect();
        this.getParent().dispatchEvent(e);
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
