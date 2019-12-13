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
import java.util.Set;
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
    private MemoryRecorder memoryRecorder;
    private JFrame memoryFrame;

    public MemoryPanel(MemoryPage memoryPage, User user, MemoryRecorder memoryRecorder) {
        super(GroundPanelTemplate.BACK);
        this.memoryPage = memoryPage;
        this.user = user;
        this.memoryRecorder = memoryRecorder;
        setProperty();
        addComponents();
    }

    public MemoryPage getMemoryPage() {
        return memoryPage;
    }

    public JFrame getMemoryFrame() {
        return memoryFrame;
    }

    public void setProperty() {
        setLayout(new GridBagLayout());
    }

    public void addComponents() {
        memoryFrame = new JFrame("Learning");
        setSize(memoryFrame, 1200, 720);
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

        addButtonsPanel(memoryFrame);

        memoryFrame.add(this);
        memoryFrame.setVisible(true);
    }

    private void addProgressPanel() {

        JPanel progressPanel = new GroundPanelTemplate(GroundPanelTemplate.FORE);
        progressPanel.setLayout(new BorderLayout());

        JLabel lbl_mP_newLearnNumber = new JLabel("       New:  " + memoryPage.getLearnNumber() + "       ", SwingConstants.CENTER);
        progressPanel.add(lbl_mP_newLearnNumber, BorderLayout.NORTH);

        JLabel lbl_mP_turnNumber = new JLabel("       Review: " + memoryPage.getReviewNumber() + "       ", SwingConstants.CENTER);
        progressPanel.add(lbl_mP_turnNumber, BorderLayout.SOUTH);

        add(progressPanel, new GridBagTool().setFill(GridBagConstraints.NONE).setAnchor(GridBagConstraints.SOUTHWEST).setGridx(1).setGridy(1).setGridwidth(1).setGridheight(1).setWeightx(0.45).setWeighty(0.1));
    }

    private void addVocabularyPanel() {

        JPanel vocabularyPanel = new GroundPanelTemplate(GroundPanelTemplate.FORE);
        vocabularyPanel.setLayout(new GridBagLayout());

        JLabel lbl_mP_word = new JLabel(memoryPage.getWord(), SwingConstants.CENTER);
        lbl_mP_word.setFont(new Font("FACE_SYSTEM", Font.PLAIN, 40));
        vocabularyPanel.add(lbl_mP_word, new GridBagTool().setFill(GridBagConstraints.NONE).setAnchor(GridBagConstraints.SOUTH).setGridx(0).setGridy(0).setGridwidth(1).setGridheight(1).setWeightx(1).setWeighty(0.5));

        JLabel lbl_mP_phoneticSymbol = new JLabel("/" + memoryPage.getPhoneticSymbol() + "/", SwingConstants.CENTER);
        lbl_mP_phoneticSymbol.setFont(new Font("FACE_SYSTEM", Font.PLAIN, 20));
        vocabularyPanel.add(lbl_mP_phoneticSymbol, new GridBagTool().setFill(GridBagConstraints.NONE).setAnchor(GridBagConstraints.NORTH).setGridx(0).setGridy(1).setGridwidth(1).setGridheight(1).setWeightx(1).setWeighty(0.5));

        add(vocabularyPanel, new GridBagTool().setFill(GridBagConstraints.BOTH).setAnchor(GridBagConstraints.SOUTH).setGridx(1).setGridy(2).setGridwidth(2).setGridheight(1).setWeightx(0.9).setWeighty(0.35));
    }

    private void addChoicesPanel() {

        JPanel choicesPanel = new GroundPanelTemplate(GroundPanelTemplate.FORE);
        choicesPanel.setLayout(new GridLayout(2, 2));

        Set<Word> choicesInDB = memoryPage.getChoices();
        for (Word choice : choicesInDB) {
            ChoiceLabel lbl_mP_choice = new ChoiceLabel(choice, user, memoryPage.getWordObj(), memoryPage, memoryRecorder, memoryFrame);
            choicesPanel.add(lbl_mP_choice);
        }

        add(choicesPanel, new GridBagTool().setGridx(1).setGridy(3).setGridwidth(2).setGridheight(2).setWeightx(0.9).setWeighty(0.35));
    }

    private void addButtonsPanel(JFrame memoryFrame) {
        JPanel buttonsPanel = new GroundPanelTemplate(GroundPanelTemplate.FORE);

        JButton btn_mP_hint = new JButton("Prompt");
        btn_mP_hint.addActionListener(new WordDetailController(user, new WordExplainPage(memoryPage.getWord()), this, memoryRecorder));
        buttonsPanel.add(btn_mP_hint);

        JButton btn_mP_favorite = new JButton("Favorite");
        btn_mP_favorite.addActionListener(new AddFavoriteController(user, memoryPage.getWordObj()));
        buttonsPanel.add(btn_mP_favorite);

        add(buttonsPanel, new GridBagTool().setFill(GridBagConstraints.NONE).setAnchor(GridBagConstraints.NORTHWEST).setGridx(1).setGridy(5).setGridwidth(1).setGridheight(1).setWeightx(0.45).setWeighty(0.1));

    }
}


