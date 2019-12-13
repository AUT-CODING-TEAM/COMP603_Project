/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.memory;

import controller.interfaces.UserController;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import model.*;
import view.main.MainView;
import view.memory.ChoiceLabel;
import view.memory.MemoryPanel;

/**
 *
 * @author ThinkPad
 */
public class MakeChoiceController implements MouseListener{
    
    private MemoryRecorder memoryRecorder;
    private User user;
    private JFrame memoryFrame;

    public MakeChoiceController(MemoryRecorder memoryRecorder, User user, JFrame memoryFrame) {
        this.memoryRecorder = memoryRecorder;
        this.user = user;
        this.memoryFrame = memoryFrame;
    }
    

    @Override
    public void mouseClicked(MouseEvent e) {
        ChoiceLabel selectedLabel = (ChoiceLabel)e.getSource();
        if (new UserController().checkAns(selectedLabel.getUser(), selectedLabel.getWordObj(), selectedLabel.getChoice())) {
//            JOptionPane.showMessageDialog(null, "Correct!", "information ", JOptionPane.INFORMATION_MESSAGE);
        }
        else{
            JOptionPane.showMessageDialog(null, "Wrong! Right Answer: " + selectedLabel.getWordObj().getChinese(), "error ", JOptionPane.ERROR_MESSAGE);
            memoryRecorder.getWordsToReviewFromPromOrErr().add(selectedLabel.getWordObj());
        }
        
        MemoryPage memoryPage = memoryRecorder.next();

        if (memoryPage == null) {    
            JOptionPane.showMessageDialog(null, "Finish!", "information ", JOptionPane.INFORMATION_MESSAGE);
            new MainView(user);
        }
        else{
            new MemoryPanel(memoryPage, user, memoryRecorder);
        }
        
        memoryFrame.dispose();
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
