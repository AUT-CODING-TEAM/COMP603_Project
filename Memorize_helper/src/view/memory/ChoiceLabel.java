/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.memory;

import controller.memory.MakeChoiceController;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import model.*;

/**
 *
 * @author ThinkPad
 */
public class ChoiceLabel extends JLabel {
    private MemoryPanel memoryPanel;
    private Word choice;
    private Word wordObj;
    private User user;
    private MemoryPage memoryPage;

    ChoiceLabel(Word choice, User user, MemoryPanel memoryPanel, boolean isChineseChoice) {
        super(choice.getChinese(), SwingConstants.CENTER);
        if (!isChineseChoice) {
            this.setText(choice.getWord());
        }
        this.memoryPanel = memoryPanel;
        this.choice = choice;
        this.user = user;
        this.memoryPage = memoryPanel.getMemoryPage();
        this.wordObj = memoryPage.getWordObj();
        setBorder(new TitledBorder(""));
        addMouseListener(new MakeChoiceController(memoryPanel.getMemoryRecorder(), user, memoryPanel.getMemoryFrame()));
    }

    public MemoryPage getMemoryPage() {
        return memoryPage;
    }

    public Word getChoice() {
        return choice;
    }

    public Word getWordObj() {
        return wordObj;
    }

    public User getUser() {
        return user;
    }
    
    
}
