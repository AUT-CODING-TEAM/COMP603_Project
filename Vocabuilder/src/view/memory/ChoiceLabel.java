/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.memory;

import controller.memory.MakeChoiceController;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import model.*;

/**
 *
 * @author ThinkPad
 */
public class ChoiceLabel extends JLabel implements MouseListener {

    private MemoryPanel memoryPanel;
    private Word choice;
    private Word wordObj;
    private User user;
    private MemoryPage memoryPage;

    private static final Color DEFAULT_COLOR = new Color(248, 246, 241);
    private static final Color ENTERED_COLOR = new Color(238, 237, 233);
    private static final Color PRESSED_COLOR = new Color(222, 221, 218);

    ChoiceLabel(Word choice, User user, MemoryPanel memoryPanel, boolean isChineseChoice) {
//        setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        this.setBackground(DEFAULT_COLOR);
        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(89,86,79)));
        this.setText("<html>" + choice.getChinese() + "</html>");
        this.setHorizontalAlignment(SwingConstants.CENTER);
        if (!isChineseChoice) {
            this.setText("<html>" + choice.getWord() + "</html>");
        }
        this.setOpaque(true);
        this.memoryPanel = memoryPanel;
        this.choice = choice;
        this.user = user;
        this.memoryPage = memoryPanel.getMemoryPage();
        this.wordObj = memoryPage.getWordObj();
        this.addMouseListener(this);
//        setBorder(new TitledBorder(""));
        MakeChoiceController t = new MakeChoiceController(memoryPanel.getMemoryRecorder(), user, memoryPanel.getMemoryFrame());
        addMouseListener(t);
        addMouseMotionListener(t);

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

    /**
     *
     * mouseState: 1. pressed 2. released 3. Entered 4. Exited
     */
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.setBackground(PRESSED_COLOR);
//        setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    //    this.setBackground(ENTERED_COLOR);
//        setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        this.setBackground(ENTERED_COLOR);

    }

    @Override
    public void mouseExited(MouseEvent e) {
        this.setBackground(DEFAULT_COLOR);
//        setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
    }


}
