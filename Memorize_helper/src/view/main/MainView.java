/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.main;

import view.prepare.*;
import java.awt.*;
import javax.swing.*;
import model.*;
import view.*;

/**
 *
 * @author ThinkPad
 */
public class MainView extends JFrame {

    private User user;

    public MainView(User user) {
        this.user = user;
        setProperty();
        addComponents();
        setVisible(true);
    }

    private void setSize() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
//        int frameWidth = 1280;
        int frameWidth = 720;
        int frameHeight = 720;
        setBounds((screenWidth - frameWidth) / 2, (screenHeight - frameHeight) / 2, frameWidth, frameHeight);
    }

    private void setProperty() {
        setSize();
        setTitle("Memorize Helper");
//        setLayout(new GridBagLayout());

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
        
        TopPanel topPanel = new TopPanel(this, user);
        jPanel.add(topPanel, new GridBagTool().setGridx(1).setGridy(1).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.1));

        CenterPanel centralPanel = new CenterPanel(this, user);
        jPanel.add(centralPanel, new GridBagTool().setGridx(1).setGridy(2).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.6));

        BottomPanel bottomPanel = new BottomPanel(this, user);
        jPanel.add(bottomPanel, new GridBagTool().setGridx(1).setGridy(3).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.2));
        
        add(jPanel);
    }

    public static void main(String[] args) {
        new LoginDialog();
    }
}
