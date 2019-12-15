/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.main;

import controller.interfaces.PlanController;
import database.Database;
import view.prepare.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import javax.swing.*;
import model.*;
import view.*;

/**
 *
 * @author ThinkPad, Pingchuan.Huang
 */
public class MainView extends JFrame {

    private User user;
    private TopPanel topPanel;
    private CenterPanel centralPanel;
    private BottomPanel bottomPanel;
    private PlanController pc;

    public MainView(User user) {
        this.user = user;
        this.pc = new PlanController();
        setProperty();
        addComponents();
        setVisible(true);
    }

    private void setSize() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
        int frameWidth = 720;
        int frameHeight = 720;
        setBounds((screenWidth - frameWidth) / 2, (screenHeight - frameHeight) / 2, frameWidth, frameHeight);
    }

    private void setProperty() {
        setSize();
        setTitle("Vocabulearner");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void addComponents() {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridBagLayout());
        jPanel.setOpaque(true);
        jPanel.setBackground(Color.decode("#EEECE8"));

        //left fill label
        jPanel.add(new JLabel(), new GridBagTool().setGridx(0).setGridy(0).setGridwidth(1).setGridheight(5).setWeightx(0.05).setWeighty(1));
        //right fill label
        jPanel.add(new JLabel(), new GridBagTool().setGridx(2).setGridy(0).setGridwidth(1).setGridheight(5).setWeightx(0.05).setWeighty(1));
        //top fill label
        jPanel.add(new JLabel(), new GridBagTool().setGridx(1).setGridy(0).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.05));
        //bottom fill label
        jPanel.add(new JLabel(), new GridBagTool().setGridx(1).setGridy(4).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.05));
        pc.updateTodayPlanInfo(this.user);

        topPanel = new TopPanel(this, user);

        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                MainView.this.topPanel.getTf_tP_keyword().requestFocus();
            }
        });

        jPanel.add(topPanel, new GridBagTool().setGridx(1).setGridy(1).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.1));

        centralPanel = new CenterPanel(this, user);
        jPanel.add(centralPanel, new GridBagTool().setGridx(1).setGridy(2).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.6));

        bottomPanel = new BottomPanel(this, user);
        jPanel.add(bottomPanel, new GridBagTool().setGridx(1).setGridy(3).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.2));

        add(jPanel);
    }

    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);
        //init the database
        Database db = Database.getInstance();
        db.init();
        new LoginDialog();
    }
}
