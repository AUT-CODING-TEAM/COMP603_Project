/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.rankList;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import model.*;
import view.*;

/**
 *
 * @author ThinkPad
 */
public class RankListPanel extends GroundPanelTemplate {

    private JFrame mainView;
    private RankInfo rankInfo;

    public RankListPanel(JFrame mainView, RankInfo rankInfo) {
        super(GroundPanelTemplate.BACK);
        this.mainView = mainView;
        this.rankInfo = rankInfo;
//        setBorder(new TitledBorder("RankListPanel"));
        setProperty();
        addComponents();
    }

    private void setSize(JFrame jFrame) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
//        int frameWidth = 1280;
        int frameWidth = 720;
        int frameHeight = 720;
        jFrame.setBounds((screenWidth - frameWidth) / 2, (screenHeight - frameHeight) / 2, frameWidth, frameHeight);
    }

    public void setProperty() {
        setLayout(new GridBagLayout());
    }

    public void addComponents() {
        JFrame rankListFrame = new JFrame("排行榜");
        setSize(rankListFrame);
        rankListFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        rankListFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
//                super.windowClosing(e);
                mainView.setVisible(true);
            }
        });

        //top fill label
        add(new JLabel(), new GridBagTool().setGridx(0).setGridy(0).setGridwidth(1).setGridheight(1).setWeightx(1).setWeighty(0.05));

        JLabel lbl_rLP_rankList = new JLabel("排行榜", SwingConstants.CENTER);
        add(lbl_rLP_rankList, new GridBagTool().setFill(GridBagConstraints.VERTICAL).setAnchor(GridBagConstraints.CENTER).setGridx(0).setGridy(1).setGridwidth(1).setGridheight(1).setWeightx(1).setWeighty(0.1));

        JPanel jPanel = new GroundPanelTemplate(GroundPanelTemplate.FORE);
        jPanel.setLayout(new GridLayout(1, 3));
        
        JList list_rLP_rankListPart1 = new ListInScrollTemplate(rankInfo.getRankInfo(RankInfo.NUMBERS));
        jPanel.add(list_rLP_rankListPart1);
        
        JList list_rLP_rankListPart2 = new ListInScrollTemplate(rankInfo.getRankInfo(RankInfo.NAMES));
        jPanel.add(list_rLP_rankListPart2);
        
        JList list_rLP_rankListPart3 = new ListInScrollTemplate(rankInfo.getRankInfo(RankInfo.WORDS));
        jPanel.add(list_rLP_rankListPart3);

        JScrollPane jScrollPane = new JScrollPane(jPanel);
        add(jScrollPane, new GridBagTool().setGridx(0).setGridy(2).setGridwidth(1).setGridheight(1).setWeightx(1).setWeighty(0.85));

        rankListFrame.add(this);
        rankListFrame.setVisible(true);
    }
}
