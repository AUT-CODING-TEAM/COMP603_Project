/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.main;

import cotroller.main.StartLearnController;
import java.awt.*;
import javax.swing.*;
import model.User;
import view.GridBagTool;

/**
 *
 * @author ThinkPad
 */
public class BottomPanel extends MainViewViewTemplate {

    public BottomPanel(JFrame mainView, User user) {
        super(mainView, user);
    }

    private void setSize() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
        int frameWidth = 1280;
        int frameHeight = 120;
        setBounds((screenWidth - frameWidth) / 2, (screenHeight - frameHeight) / 2, frameWidth, frameHeight);
    }

    @Override
    public void setProperty() {
//        setSize();
        setLayout(new GridBagLayout());
//        setBorder(new TitledBorder("BottomPanel"));
        setOpaque(true);
        setBackground(Color.decode("#EEECE8"));
    }

    @Override
    public void addComponents() {
        JLabel lbl_bP_todayPlan = new JLabel();
        int todayLearnedNumber = user.getTodayLearnedNumber();
        int todayTargetNumber = user.getTodayTargetNumber();
        int todayReviewNumber = user.getTodayReviewNumber();
        String todayPlan = "今日需新学" + todayLearnedNumber + "/" + todayTargetNumber + " " + "  今日需复习" + todayReviewNumber;
        lbl_bP_todayPlan.setText(todayPlan);
        lbl_bP_todayPlan.setHorizontalAlignment(SwingConstants.CENTER);
        add(lbl_bP_todayPlan, new GridBagTool().setFill(GridBagConstraints.NONE).setAnchor(GridBagConstraints.CENTER).setGridx(0).setGridy(0).setGridwidth(1).setGridheight(1).setWeightx(1).setWeighty(0.5));

        JButton btn_bP_startLearn = new JButton("    开始背单词吧    ");
        btn_bP_startLearn.addActionListener(new StartLearnController(mainView, user));
        add(btn_bP_startLearn, new GridBagTool().setFill(GridBagConstraints.NONE).setAnchor(GridBagConstraints.CENTER).setGridx(0).setGridy(1).setGridwidth(1).setGridheight(1).setWeightx(1).setWeighty(0.5));
    }
}
