/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.vocabularyList;

import controller.StartLearnController;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import model.*;
import view.*;

/**
 *
 * @author ThinkPad
 */
public class VocabularyListPanel extends GroundPanelTemplate {
    
    private JFrame mainView;
    private User user;
    private JFrame vocabularyListFrame;
    
    public VocabularyListPanel(JFrame mainView, User user) {
        super(GroundPanelTemplate.BACK);
        this.mainView = mainView;
        this.user = user;
        setProperty();
        addComponents();
    }
    
    public void setProperty() {
        setLayout(new GridBagLayout());
    }
    
    public void addComponents() {
        vocabularyListFrame = new JFrame("Word List");
        setSize(vocabularyListFrame, 720, 720);
        vocabularyListFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        vocabularyListFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                mainView.setVisible(true);
            }
        });

        //left fill label
        add(new JLabel(), new GridBagTool().setGridx(0).setGridy(0).setGridwidth(1).setGridheight(4).setWeightx(0.05).setWeighty(1));
        //right fill label
        add(new JLabel(), new GridBagTool().setGridx(2).setGridy(0).setGridwidth(1).setGridheight(4).setWeightx(0.05).setWeighty(1));
        //top fill label
        add(new JLabel(), new GridBagTool().setGridx(1).setGridy(0).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.05));
        //bottom fill label
        add(new JLabel(), new GridBagTool().setGridx(1).setGridy(3).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.05));
        
        JLabel lbl_vLP_vocabularyList = new JLabel("Word List", SwingConstants.CENTER);
        add(lbl_vLP_vocabularyList, new GridBagTool().setFill(GridBagConstraints.NONE).setAnchor(GridBagConstraints.CENTER).setGridx(1).setGridy(1).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.1));
        
        JTabbedPane topTabs = new JTabbedPane();
        topTabs.addTab("Learned", new VocabularyListTabPanel(VocabularyListInfo.STUDIED));
        topTabs.addTab("All Words", new VocabularyListTabPanel(VocabularyListInfo.ALL_WORDS));
        topTabs.addTab("Favorites", new VocabularyListTabPanel(VocabularyListInfo.COLLECT));
        add(topTabs, new GridBagTool().setGridx(1).setGridy(2).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.8));
        
        vocabularyListFrame.add(this);
        vocabularyListFrame.setVisible(true);
    }
    
    class VocabularyListTabPanel extends GroundPanelTemplate {
        
        private JLabel lbl_vLTP_vocabularyTotalNumber;
        private JList list_vLTP_vocabularyList;
        private VocabularyListInfo vocabularyListInfo;
        private JPanel jPanel;
        
        public VocabularyListTabPanel(int option) {
            super(GroundPanelTemplate.FORE);
            
            vocabularyListInfo = new VocabularyListInfo(option, user);
            
            setProperty();
            addComponents();
            
            lbl_vLTP_vocabularyTotalNumber.setText("Total Number of Words: " + String.valueOf(vocabularyListInfo.getVocabularyListInfo().length));
            list_vLTP_vocabularyList.setListData(vocabularyListInfo.getVocabularyListInfo());
            if (option == 2) {
                JButton btn_vLTP_learnCollect = new JButton("Start Learning My Favorites");
                btn_vLTP_learnCollect.addActionListener(new StartLearnController(vocabularyListFrame, user, "favorite"));
                jPanel.add(btn_vLTP_learnCollect, BorderLayout.EAST);
            }
        }
        
        public void setProperty() {
            setLayout(new GridBagLayout());
        }
        
        public void addComponents() {
            //left fill label
            add(new JLabel(), new GridBagTool().setGridx(0).setGridy(0).setGridwidth(1).setGridheight(1).setWeightx(0.05).setWeighty(0.1));
            //right fill label
            add(new JLabel(), new GridBagTool().setGridx(2).setGridy(0).setGridwidth(1).setGridheight(1).setWeightx(0.05).setWeighty(0.1));
            
            lbl_vLTP_vocabularyTotalNumber = new JLabel();

            jPanel = new GroundPanelTemplate(GroundPanelTemplate.FORE);
            jPanel.setLayout(new BorderLayout());
            jPanel.add(lbl_vLTP_vocabularyTotalNumber, BorderLayout.WEST);
            add(jPanel, new GridBagTool().setFill(GridBagConstraints.HORIZONTAL).setAnchor(GridBagConstraints.CENTER).setGridx(1).setGridy(0).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.1));
            
            JPanel vocabularyListTabPanel = new GroundPanelTemplate(GroundPanelTemplate.FORE);
            
            list_vLTP_vocabularyList = new ListInScrollTemplate(vocabularyListInfo.getVocabularyListInfo());
            vocabularyListTabPanel.add(list_vLTP_vocabularyList);
            
            JScrollPane jScrollPane = new JScrollPane(vocabularyListTabPanel);
            jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            add(jScrollPane, new GridBagTool().setGridx(0).setGridy(1).setGridwidth(3).setGridheight(1).setWeightx(1).setWeighty(0.9));
        }
    }
}
