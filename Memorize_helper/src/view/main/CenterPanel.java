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
    
    private void setSize() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
        int frameWidth = 1280;
        int frameHeight = 480;
        setBounds((screenWidth - frameWidth) / 2, (screenHeight - frameHeight) / 2, frameWidth, frameHeight);
    }
    
    @Override
    public void setProperty() {
        //        setSize();
//        setLayout(new BorderLayout());
//        setBorder(new TitledBorder("CenterPanel"));
        setLayout(new GridBagLayout());
        setOpaque(true);
        setBackground(Color.decode("#F8F6F1"));
    }
    
    @Override
    public void addComponents() {
        //left fill label
        add(new JLabel(), new GridBagTool().setGridx(0).setGridy(0).setGridwidth(1).setGridheight(5).setWeightx(0.05).setWeighty(1));
        //right fill label
        add(new JLabel(), new GridBagTool().setGridx(3).setGridy(0).setGridwidth(1).setGridheight(5).setWeightx(0.05).setWeighty(1));
        
        JLabel lbl_cP_remainingDayPart1 = new JLabel("剩余", SwingConstants.CENTER);
        add(lbl_cP_remainingDayPart1, new GridBagTool().setGridx(1).setGridy(0).setGridwidth(1).setGridheight(1).setWeightx(0.45).setWeighty(0.15));
        
        JLabel lbl_cP_todayTargetNumberPart1 = new JLabel("今日单词", SwingConstants.CENTER);
        add(lbl_cP_todayTargetNumberPart1, new GridBagTool().setGridx(2).setGridy(0).setGridwidth(1).setGridheight(1).setWeightx(0.45).setWeighty(0.15));
        
        addRemainingDayRest();
        
        addTodayTargetNumberRest();
        
        JLabel lbl_cP_studyPlan = new JLabel("当前计划：" + user.getStudyPlan().getStudyPlanName(), SwingConstants.CENTER);
        add(lbl_cP_studyPlan, new GridBagTool().setGridx(1).setGridy(2).setGridwidth(1).setGridheight(1).setWeightx(0.45).setWeighty(0.2));
        
        JButton btn_cP_changePlan = new JButton("修改计划");
        btn_cP_changePlan.addActionListener(new ChangePlanController(mainView, user));
        add(btn_cP_changePlan, new GridBagTool().setFill(GridBagConstraints.NONE).setGridx(2).setGridy(2).setGridwidth(1).setGridheight(1).setWeightx(0.45).setWeighty(0.2));
        
        JLabel lbl_cP_finishedNumber = new JLabel("已学完" + user.getFinishedNumberInPlan() + "/" + user.getStudyPlan().getTotalNumber(), SwingConstants.CENTER);
        add(lbl_cP_finishedNumber, new GridBagTool().setGridx(1).setGridy(3).setGridwidth(1).setGridheight(1).setWeightx(0.45).setWeighty(0.2));
        
        JButton btn_cP_vocabulary = new JButton("单词列表");
        btn_cP_vocabulary.addActionListener(new ShowVocabularyController(mainView, user));
        add(btn_cP_vocabulary, new GridBagTool().setFill(GridBagConstraints.NONE).setGridx(2).setGridy(3).setGridwidth(1).setGridheight(1).setWeightx(0.45).setWeighty(0.2));
        
        addProgressBar();
    }
    
    private void addRemainingDayRest() {
        JPanel jPanel = new GroundPanelTemplate(GroundPanelTemplate.FORE);

        JLabel lbl_cP_remainingDayPart2 = new JLabel(String.valueOf(user.getRemainingDay()), SwingConstants.CENTER);
        lbl_cP_remainingDayPart2.setFont(new Font("FACE_SYSTEM", Font.PLAIN, 40));
        jPanel.add(lbl_cP_remainingDayPart2);

        JLabel lbl_cP_remainingDayPart3 = new JLabel("天", SwingConstants.CENTER);
        jPanel.add(lbl_cP_remainingDayPart3);

        add(jPanel, new GridBagTool().setGridx(1).setGridy(1).setGridwidth(1).setGridheight(1).setWeightx(0.45).setWeighty(0.3));
    }
    
    private void addTodayTargetNumberRest() {
        JPanel jPanel = new GroundPanelTemplate(GroundPanelTemplate.FORE);

        JLabel lbl_cP_todayTargetNumberPart2 = new JLabel(String.valueOf(user.getTodayTargetNumber()), SwingConstants.CENTER);
        lbl_cP_todayTargetNumberPart2.setFont(new Font("FACE_SYSTEM", Font.PLAIN, 40));
        jPanel.add(lbl_cP_todayTargetNumberPart2);

        JLabel lbl_cP_todayTargetNumberPart3 = new JLabel("个", SwingConstants.CENTER);
        jPanel.add(lbl_cP_todayTargetNumberPart3);

        add(jPanel, new GridBagTool().setGridx(2).setGridy(1).setGridwidth(1).setGridheight(1).setWeightx(0.45).setWeighty(0.3));
    }
    
    private void addProgressBar() {
        JProgressBar psBar_cP_progress = new JProgressBar(0, 100);
        int progress = user.getFinishedNumberInPlan() * 100 / user.getStudyPlan().getTotalNumber();
        System.out.println("progress = " + progress);
        psBar_cP_progress.setValue(progress);
        psBar_cP_progress.setOpaque(true);
        psBar_cP_progress.setBackground(Color.decode("#F8F6F1"));
        add(psBar_cP_progress, new GridBagTool().setFill(GridBagConstraints.HORIZONTAL).setGridx(1).setGridy(4).setGridwidth(2).setGridheight(1).setWeightx(0.9).setWeighty(0.15));
    }
}
