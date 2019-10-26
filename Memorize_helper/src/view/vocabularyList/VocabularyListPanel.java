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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
        vocabularyListFrame = new JFrame("单词列表");
        setSize(vocabularyListFrame);
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
        
        JLabel lbl_vLP_vocabularyList = new JLabel("单词列表", SwingConstants.CENTER);
        add(lbl_vLP_vocabularyList, new GridBagTool().setFill(GridBagConstraints.NONE).setAnchor(GridBagConstraints.CENTER).setGridx(1).setGridy(1).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.1));
        
        JTabbedPane topTabs = new JTabbedPane();
        topTabs.addTab("已学单词", new VocabularyListTabPanel(VocabularyListInfo.STUDIED));
        topTabs.addTab("未学单词", new VocabularyListTabPanel(VocabularyListInfo.UNSTUDIED));
        topTabs.addTab("已斩单词", new VocabularyListTabPanel(VocabularyListInfo.CUT));
        topTabs.addTab("收藏单词", new VocabularyListTabPanel(VocabularyListInfo.COLLECT));
        add(topTabs, new GridBagTool().setGridx(1).setGridy(2).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.8));
        
        vocabularyListFrame.add(this);
        vocabularyListFrame.setVisible(true);
    }
    
    class VocabularyListTabPanel extends GroundPanelTemplate {
        
        private JLabel lbl_vLTP_vocabularyTotalNumber;
        private JList list_vLTP_vocabularyListPart1;
        private JList list_vLTP_vocabularyListPart2;
        private VocabularyListInfo vocabularyListInfo;
        private JPanel jPanel;
        
        public VocabularyListTabPanel(int option) {
            super(GroundPanelTemplate.FORE);
            
            vocabularyListInfo = new VocabularyListInfo(option);
            
            setProperty();
            addComponents();
            
            lbl_vLTP_vocabularyTotalNumber.setText("单词总数:" + String.valueOf(vocabularyListInfo.getVocabularyListInfo(0).length));
            list_vLTP_vocabularyListPart1.setListData(vocabularyListInfo.getVocabularyListInfo(0));
            list_vLTP_vocabularyListPart2.setListData(vocabularyListInfo.getVocabularyListInfo(1));
            if (option == 3) {
                JButton btn_vLTP_learnCollect = new JButton("学习");
                btn_vLTP_learnCollect.addActionListener(new StartLearnController(mainView, user, vocabularyListFrame));
//                add(btn_vLTP_learnCollect, new GridBagTool().setFill(GridBagConstraints.NONE).setGridx(2).setGridy(0).setGridwidth(1).setGridheight(1).setWeightx(0.45).setWeighty(0.1));
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
//            add(lbl_vLTP_vocabularyTotalNumber, new GridBagTool().setFill(GridBagConstraints.VERTICAL).setAnchor(GridBagConstraints.WEST).setGridx(1).setGridy(0).setGridwidth(1).setGridheight(1).setWeightx(0.45).setWeighty(0.1));

            jPanel = new GroundPanelTemplate(GroundPanelTemplate.FORE);
            jPanel.setLayout(new BorderLayout());
            jPanel.add(lbl_vLTP_vocabularyTotalNumber, BorderLayout.WEST);
            add(jPanel, new GridBagTool().setFill(GridBagConstraints.HORIZONTAL).setAnchor(GridBagConstraints.CENTER).setGridx(1).setGridy(0).setGridwidth(1).setGridheight(1).setWeightx(0.9).setWeighty(0.1));
            
            JPanel vocabularyListTabPanel = new GroundPanelTemplate(GroundPanelTemplate.FORE);
            vocabularyListTabPanel.setLayout(new GridLayout(1, 2));
            
            list_vLTP_vocabularyListPart1 = new ListInScrollTemplate(vocabularyListInfo.getVocabularyListInfo(0));
            vocabularyListTabPanel.add(list_vLTP_vocabularyListPart1);
            
            list_vLTP_vocabularyListPart2 = new ListInScrollTemplate(vocabularyListInfo.getVocabularyListInfo(1));
//            list_vLTP_vocabularyListPart2.setEnabled(true);
//            list_vLTP_vocabularyListPart2.setBackground(new Color(51,51,51));
//            list_vLTP_vocabularyListPart2.addListSelectionListener(new ListSelectionListener() {
//                @Override
//                public void valueChanged(ListSelectionEvent e) {
//                    if (list_vLTP_vocabularyListPart2.getValueIsAdjusting()) {
//                        JLabel selectedItem = (JLabel)e.getSource();
//                        System.out.println(e.getSource());
//                        if (selectedItem.getBackground().equals(new Color(51,51,51))) {
//                            selectedItem.setBackground(new Color(248,246,241));
//                        }
//                        else if (selectedItem.getBackground().equals(new Color(248,246,241))) {
//                            selectedItem.setBackground(new Color(51,51,51));
//                        }
//                    }
//                }
//            });
            vocabularyListTabPanel.add(list_vLTP_vocabularyListPart2);
            
            JScrollPane jScrollPane = new JScrollPane(vocabularyListTabPanel);
//            add(jScrollPane, new GridBagTool().setGridx(0).setGridy(1).setGridwidth(4).setGridheight(1).setWeightx(1).setWeighty(0.9));
            add(jScrollPane, new GridBagTool().setGridx(0).setGridy(1).setGridwidth(3).setGridheight(1).setWeightx(1).setWeighty(0.9));
        }
    }
}
