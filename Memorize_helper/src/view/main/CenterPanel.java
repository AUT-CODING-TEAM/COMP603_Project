/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.main;

import controller.main.*;
import java.awt.*;
import javax.swing.*;
import model.*;
import view.*;

/**
 *
 * @author ThinkPad
 */
public class CenterPanel extends MainViewViewTemplate {

    public CenterPanel(JFrame mainView, User user) {
        super(mainView, user);
    }

    @Override
    public void setProperty() {
//        setBorder(new TitledBorder("CenterPanel"));
        setLayout(new GridBagLayout());
        setOpaque(true);
        setBackground(Color.decode("#F8F6F1"));
    }

    @Override
    public void addComponents() {
        StudyPlan sp = user.getCurrentStudyPlan();
        //left fill label
        add(new JLabel(), new GridBagTool().setGridx(0).setGridy(0).setGridwidth(1).setGridheight(5).setWeightx(0.05).setWeighty(1));
        //right fill label
        add(new JLabel(), new GridBagTool().setGridx(3).setGridy(0).setGridwidth(1).setGridheight(5).setWeightx(0.05).setWeighty(1));

        JLabel lbl_cP_remainingDayPart1 = new JLabel("Remaining", SwingConstants.CENTER);
        add(lbl_cP_remainingDayPart1, new GridBagTool().setGridx(1).setGridy(0).setGridwidth(1).setGridheight(1).setWeightx(0.45).setWeighty(0.15));

        JLabel lbl_cP_todayTargetNumberPart1 = new JLabel("Today's", SwingConstants.CENTER);
        add(lbl_cP_todayTargetNumberPart1, new GridBagTool().setGridx(2).setGridy(0).setGridwidth(1).setGridheight(1).setWeightx(0.45).setWeighty(0.15));

        addRemainingDayRest();

        addTodayTargetNumberRest();

        JLabel lbl_cP_studyPlan = new JLabel("Current Plan: " + user.getCurrentStudyPlan().getStudyPlanName(), SwingConstants.CENTER);
        add(lbl_cP_studyPlan, new GridBagTool().setGridx(1).setGridy(2).setGridwidth(1).setGridheight(1).setWeightx(0.45).setWeighty(0.2));

        JButton btn_cP_changePlan = new JButton("Switch Plan");
        btn_cP_changePlan.addActionListener(new ChangePlanController(mainView, user));
        add(btn_cP_changePlan, new GridBagTool().setFill(GridBagConstraints.NONE).setGridx(2).setGridy(2).setGridwidth(1).setGridheight(1).setWeightx(0.45).setWeighty(0.2));

        JLabel lbl_cP_finishedNumber = new JLabel("Learned: " + sp.getTotalMemorizedNumber() + "/" + user.getCurrentStudyPlan().getTotalNumber(), SwingConstants.CENTER);
        add(lbl_cP_finishedNumber, new GridBagTool().setGridx(1).setGridy(3).setGridwidth(1).setGridheight(1).setWeightx(0.45).setWeighty(0.2));

        JButton btn_cP_vocabulary = new JButton("Word List");
        btn_cP_vocabulary.addActionListener(new ShowVocabularyListController(mainView, user));
        add(btn_cP_vocabulary, new GridBagTool().setFill(GridBagConstraints.NONE).setGridx(2).setGridy(3).setGridwidth(1).setGridheight(1).setWeightx(0.45).setWeighty(0.2));

        addProgressBar();
    }

    private void addRemainingDayRest() {
        JPanel jPanel = new GroundPanelTemplate(GroundPanelTemplate.FORE);

        JLabel lbl_cP_remainingDayPart2 = new JLabel(String.valueOf(user.getRemainingDay()), SwingConstants.CENTER);
        lbl_cP_remainingDayPart2.setFont(new Font("FACE_SYSTEM", Font.PLAIN, 40));
        jPanel.add(lbl_cP_remainingDayPart2);

        JLabel lbl_cP_remainingDayPart3 = new JLabel(user.getRemainingDay() == 1 ? "day" : "days", SwingConstants.CENTER);
        jPanel.add(lbl_cP_remainingDayPart3);

        add(jPanel, new GridBagTool().setGridx(1).setGridy(1).setGridwidth(1).setGridheight(1).setWeightx(0.45).setWeighty(0.3));
    }

    private void addTodayTargetNumberRest() {
        JPanel jPanel = new GroundPanelTemplate(GroundPanelTemplate.FORE);

        JLabel lbl_cP_todayTargetNumberPart2 = new JLabel(String.valueOf(user.getTodayTargetNumber()), SwingConstants.CENTER);
        lbl_cP_todayTargetNumberPart2.setFont(new Font("FACE_SYSTEM", Font.PLAIN, 40));
        jPanel.add(lbl_cP_todayTargetNumberPart2);

        JLabel lbl_cP_todayTargetNumberPart3 = new JLabel("words", SwingConstants.CENTER);
        jPanel.add(lbl_cP_todayTargetNumberPart3);

        add(jPanel, new GridBagTool().setGridx(2).setGridy(1).setGridwidth(1).setGridheight(1).setWeightx(0.45).setWeighty(0.3));
    }

    private void addProgressBar() {
        StudyPlan sp = user.getCurrentStudyPlan();
        int totalMemorizedNumber = sp.getTotalMemorizedNumber();
        int totalNumber = sp.getTotalNumber();
        
        JProgressBar psBar_cP_progress = new JProgressBar(0, 100);
        int progress = totalMemorizedNumber * 100 / totalNumber;
        
        psBar_cP_progress.setValue(progress);
        psBar_cP_progress.setOpaque(true);
        psBar_cP_progress.setBackground(Color.decode("#F8F6F1"));
        add(psBar_cP_progress, new GridBagTool().setFill(GridBagConstraints.HORIZONTAL).setGridx(1).setGridy(4).setGridwidth(2).setGridheight(1).setWeightx(0.9).setWeighty(0.15));
    }
}
