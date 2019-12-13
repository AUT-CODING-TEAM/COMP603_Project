/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.memory;

import controller.memory.MakeChoiceController;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import model.*;

/**
 *
 * @author ThinkPad
 */
public class ChoiceLabel extends JLabel {
    private Word choice;
    private Word wordObj;
    private User user;
    private MemoryPage memoryPage;

    ChoiceLabel(Word choice, User user, Word wordObj, MemoryPage memoryPage, MemoryRecorder memoryRecorder, JFrame memoryFrame) {
        super(choice.getChinese(), SwingConstants.CENTER);
        this.choice = choice;
        this.user = user;
        this.wordObj = wordObj;
        this.memoryPage = memoryPage;
        setBorder(new TitledBorder(""));
        addMouseListener(new MakeChoiceController(memoryRecorder, user, memoryFrame));
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
