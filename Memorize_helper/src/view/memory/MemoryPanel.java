/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.memory;

import controller.WordDetailController;
import controller.memory.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import model.*;
import view.*;
import view.main.MainView;

/**
 *
 * @author ThinkPad
 */
public class MemoryPanel extends GroundPanelTemplate {
    
    private MemoryPage memoryPage;
    private User user;
    
    public MemoryPanel(MemoryPage memoryPage, User user) {
        super(GroundPanelTemplate.BACK);
        this.memoryPage = memoryPage;
        this.user = user;
        setProperty();
        addComponents();
        
    }
    
    public void setProperty() {
        setLayout(new GridBagLayout());
    }
    
    public void addComponents() {
        JFrame memoryFrame = new JFrame("学习");
        setSize(memoryFrame, 720, 720);
        memoryFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        memoryFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
//                super.windowClosing(e);
                new MainView(user);
            }
        });

        //left fill label
        add(new JLabel(), new GridBagTool().setGridx(0).setGridy(0).setGridwidth(1).setGridheight(7).setWeightx(0.05).setWeighty(1));
        //right fill label
        add(new JLabel(), new GridBagTool().setGridx(3).setGridy(0).setGridwidth(1).setGridheight(7).setWeightx(0.05).setWeighty(1));
        //bottom fill label
        add(new JLabel(), new GridBagTool().setGridx(0).setGridy(6).setGridwidth(2).setGridheight(1).setWeightx(0.9).setWeighty(0.05));
        
        addProgressPanel();
        
        addVocabularyPanel();
        
        addChoicesPanel();
        
        JButton btn_mP_hint = new JButton("提示");
        btn_mP_hint.addActionListener(new WordDetailController(user, new WordExplainPage().setWord(memoryPage.getWord()), memoryFrame));
        add(btn_mP_hint, new GridBagTool().setFill(GridBagConstraints.NONE).setAnchor(GridBagConstraints.WEST).setGridx(1).setGridy(5).setGridwidth(1).setGridheight(1).setWeightx(0.45).setWeighty(0.1));
        
        memoryFrame.add(this);
        memoryFrame.setVisible(true);
    }
    
    private void addProgressPanel() {
        
        JPanel progressPanel = new GroundPanelTemplate(GroundPanelTemplate.FORE);
        progressPanel.setLayout(new BorderLayout());
        
        JLabel lbl_mP_newLearnNumber = new JLabel("       需新学 " + memoryPage.getTargetNumber() + "       ", SwingConstants.CENTER);
        progressPanel.add(lbl_mP_newLearnNumber, BorderLayout.NORTH);
        
        JLabel lbl_mP_turnNumber = new JLabel("       需复习 " + memoryPage.getTurnNumer() + "       ", SwingConstants.CENTER);
        progressPanel.add(lbl_mP_turnNumber, BorderLayout.SOUTH);
        
        add(progressPanel, new GridBagTool().setFill(GridBagConstraints.NONE).setAnchor(GridBagConstraints.SOUTHWEST).setGridx(1).setGridy(1).setGridwidth(1).setGridheight(1).setWeightx(0.45).setWeighty(0.1));
    }
    
    private void addVocabularyPanel() {

        JPanel vocabularyPanel = new GroundPanelTemplate(GroundPanelTemplate.FORE);
        vocabularyPanel.setLayout(new GridBagLayout());
        
        JLabel lbl_mP_word = new JLabel(memoryPage.getWord(), SwingConstants.CENTER);
        lbl_mP_word.setFont(new Font("FACE_SYSTEM", Font.PLAIN, 40));
        vocabularyPanel.add(lbl_mP_word, new GridBagTool().setFill(GridBagConstraints.NONE).setAnchor(GridBagConstraints.SOUTH).setGridx(0).setGridy(0).setGridwidth(1).setGridheight(1).setWeightx(1).setWeighty(0.5));
        
        JLabel lbl_mP_phoneticSymbol = new JLabel(memoryPage.getPhoneticSymbol(), SwingConstants.CENTER);
        lbl_mP_phoneticSymbol.setFont(new Font("FACE_SYSTEM", Font.PLAIN, 20));
        vocabularyPanel.add(lbl_mP_phoneticSymbol, new GridBagTool().setFill(GridBagConstraints.NONE).setAnchor(GridBagConstraints.NORTH).setGridx(0).setGridy(1).setGridwidth(1).setGridheight(1).setWeightx(1).setWeighty(0.5));
        
        add(vocabularyPanel, new GridBagTool().setFill(GridBagConstraints.BOTH).setAnchor(GridBagConstraints.SOUTH).setGridx(1).setGridy(2).setGridwidth(2).setGridheight(1).setWeightx(0.9).setWeighty(0.35));
    }
    
    private void addChoicesPanel() {
        
        JPanel choicesPanel = new GroundPanelTemplate(GroundPanelTemplate.FORE);
        choicesPanel.setLayout(new GridLayout(2, 2));
        
        JLabel lbl_mP_leftTopChoice = new ChoiceLabel(memoryPage.getChoices()[0]);
        choicesPanel.add(lbl_mP_leftTopChoice);
        
        JLabel lbl_mP_rightTopChoice = new ChoiceLabel(memoryPage.getChoices()[1]);
        choicesPanel.add(lbl_mP_rightTopChoice);
        
        JLabel lbl_mP_leftBottomChoice = new ChoiceLabel(memoryPage.getChoices()[2]);
        choicesPanel.add(lbl_mP_leftBottomChoice);
        
        JLabel lbl_mP_rightBottomChoice = new ChoiceLabel(memoryPage.getChoices()[3]);
        choicesPanel.add(lbl_mP_rightBottomChoice);

        add(choicesPanel, new GridBagTool().setGridx(1).setGridy(3).setGridwidth(2).setGridheight(2).setWeightx(0.9).setWeighty(0.35));
    }
}

class ChoiceLabel extends JLabel {
    public ChoiceLabel(String text) {
        super(text, SwingConstants.CENTER);
        setBorder(new TitledBorder(""));
        addMouseListener(new MakeChoiceController());
    }
}
