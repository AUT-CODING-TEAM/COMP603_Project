/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.main.MainViewControllerTemplate;
import java.awt.event.ActionEvent;
import javax.swing.*;
import model.*;
import view.memory.*;

/**
 *
 * @author ThinkPad
 */
public class StartLearnController extends MainViewControllerTemplate{
    private JFrame vocabularyListFrame;

    public StartLearnController(JFrame mainView, User user) {
        super(mainView, user);
    }
    
    //from vocabularyListFrame
    public StartLearnController(JFrame mainView, User user, JFrame vocabularyListFrame) {
        super(mainView, user);
        this.vocabularyListFrame = vocabularyListFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new MemoryPanel(new MemoryPage(), user);
        mainView.dispose();
        
        if (vocabularyListFrame != null) {
            vocabularyListFrame.dispose();
        }
    }
}
