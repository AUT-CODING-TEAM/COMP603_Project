package view.main;

import controller.StartLearnController;
import java.awt.*;
import javax.swing.*;
import model.*;
import view.*;

/**
 *
 * @author ThinkPad
 */
public class BottomPanel extends MainViewViewTemplate {

    public BottomPanel(JFrame mainView, User user) {
        super(mainView, user);
    }

    @Override
    public void setProperty() {
        setLayout(new GridBagLayout());
        setOpaque(true);
        setBackground(Color.decode("#EEECE8"));
    }

    @Override
    public void addComponents() {
        JLabel todayPlanLabel = new JLabel();
        int todayTargetNumber = user.getTodayTargetNumber();
        int todayLearnNumber = todayTargetNumber - user.getTodayLearnedNumber();
        int todayReviewNumber = user.getTodayReviewNumber();
        String todayPlan = "New Words for Today: " + (todayLearnNumber < 0 ? 0 : todayLearnNumber) + " " + "  Review for Today: " + todayReviewNumber;
        todayPlanLabel.setText(todayPlan);
        todayPlanLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(todayPlanLabel, new GridBagTool().setFill(GridBagConstraints.NONE).setAnchor(GridBagConstraints.CENTER).setGridx(0).setGridy(0).setGridwidth(1).setGridheight(1).setWeightx(1).setWeighty(0.5));

        JPanel buttonsPanel = new GroundPanelTemplate(GroundPanelTemplate.BACK);
        JButton startLearnBtn = new JButton("    Start Learning    ");
        startLearnBtn.addActionListener(new StartLearnController(mainView, user, "new"));
        buttonsPanel.add(startLearnBtn);

        JButton startReviewBtn = new JButton("    Start Reviewing    ");
        startReviewBtn.addActionListener(new StartLearnController(mainView, user, "review"));
        buttonsPanel.add(startReviewBtn);
        add(buttonsPanel, new GridBagTool().setFill(GridBagConstraints.NONE).setAnchor(GridBagConstraints.CENTER).setGridx(0).setGridy(1).setGridwidth(1).setGridheight(1).setWeightx(1).setWeighty(0.5));
    }
}
