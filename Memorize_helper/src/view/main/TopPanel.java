/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.main;

import cotroller.main.*;
import java.awt.*;
import javax.swing.*;
import model.*;
import view.*;

/**
 *
 * @author ThinkPad
 */
public class TopPanel extends MainViewViewTemplate {

    public TopPanel(JFrame mainView, User user) {
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
//        setBorder(new TitledBorder("TopPanel"));
        setOpaque(true);
        setBackground(Color.decode("#EEECE8"));
    }

    @Override
    public void addComponents() {
        //left fill label
        add(new JLabel(), new GridBagTool().setGridx(0).setGridy(0).setGridwidth(1).setGridheight(1).setWeightx(0.05).setWeighty(1));
        //right fill label
        add(new JLabel(), new GridBagTool().setGridx(4).setGridy(0).setGridwidth(1).setGridheight(1).setWeightx(0.05).setWeighty(1));
        //middle fill label
        add(new JLabel(), new GridBagTool().setGridx(2).setGridy(0).setGridwidth(1).setGridheight(1).setWeightx(0.3).setWeighty(1));

        JLabel lbl_tP_username = new JLabel("你好，" + user.getUsername());
//        lbl_tP_username.setBorder(new TitledBorder("lbl_tP_username"));
        add(lbl_tP_username, new GridBagTool().setFill(GridBagConstraints.NONE).setAnchor(GridBagConstraints.WEST).setGridx(1).setGridy(0).setGridwidth(1).setGridheight(1).setWeightx(0.3).setWeighty(1));
        //

        JPanel jPanel = new JPanel();
//        jPanel.setBorder(new TitledBorder("jPanel"));

        JLabel lbl_tP_searchTip = new JLabel("查词");
        jPanel.add(lbl_tP_searchTip);

        JTextField tf_tP_keyword = new JTextField(15);
        jPanel.add(tf_tP_keyword);

        JButton btn_tP_ok = new JButton("确定");
        btn_tP_ok.addActionListener(new SearchController(mainView, user));
        jPanel.add(btn_tP_ok);

        add(jPanel, new GridBagTool().setFill(GridBagConstraints.NONE).setAnchor(GridBagConstraints.EAST).setGridx(3).setGridy(0).setGridwidth(1).setGridheight(1).setWeightx(0.3).setWeighty(1));
    }

}
