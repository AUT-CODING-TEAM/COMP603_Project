/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
//        setBorder(new TitledBorder("BottomPanel"));
        setOpaque(true);
        setBackground(Color.decode("#EEECE8"));
    }

    @Override
    public void addComponents() {
        JLabel lbl_bP_todayPlan = new JLabel();
        int todayTargetNumber = user.getTodayTargetNumber();
        int todayLearnNumber = todayTargetNumber - user.getTodayLearnedNumber();
        int todayReviewNumber = user.getTodayReviewNumber();
        String todayPlan = "New Words for Today: " + (todayLearnNumber < 0? 0: todayLearnNumber) +  " " + "  Review for Today: " + todayReviewNumber;
        lbl_bP_todayPlan.setText(todayPlan);
        lbl_bP_todayPlan.setHorizontalAlignment(SwingConstants.CENTER);
        add(lbl_bP_todayPlan, new GridBagTool().setFill(GridBagConstraints.NONE).setAnchor(GridBagConstraints.CENTER).setGridx(0).setGridy(0).setGridwidth(1).setGridheight(1).setWeightx(1).setWeighty(0.5));

        JPanel buttonsPanel = new GroundPanelTemplate(GroundPanelTemplate.BACK);
        JButton btn_bP_startLearn = new JButton("    Start Learning    ");
        btn_bP_startLearn.addActionListener(new StartLearnController(mainView, user, "new"));
        buttonsPanel.add(btn_bP_startLearn);
        
        JButton btn_bP_startReview = new JButton("    Start Reviewing    ");
        btn_bP_startReview.addActionListener(new StartLearnController(mainView, user, "review"));
        buttonsPanel.add(btn_bP_startReview);
        add(buttonsPanel, new GridBagTool().setFill(GridBagConstraints.NONE).setAnchor(GridBagConstraints.CENTER).setGridx(0).setGridy(1).setGridwidth(1).setGridheight(1).setWeightx(1).setWeighty(0.5));
    }
}
