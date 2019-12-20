/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.interfaces.CollectionController;
import controller.interfaces.MemorizeController;
import controller.main.MainViewControllerTemplate;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import javax.swing.*;
import model.*;
import view.memory.*;

/**
 *
 * @author ThinkPad
 */
public class StartLearnController extends MainViewControllerTemplate {

    private String source;

    //maybe new learn, review or favorite, mainView is sourceView actually
    public StartLearnController(JFrame mainView, User user, String source) {
        super(mainView, user);
        this.source = source;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MemoryRecorder memoryRecorder = null;
        try {
            if (source.equals("new")) {
                memoryRecorder = new MemoryRecorder(user, new MemorizeController().getWordList(user), source);
            } else if (source.equals("review")) {
                memoryRecorder = new MemoryRecorder(user, new MemorizeController().getReviewWordLists(user), source);
            } else if (source.equals("favorite")) {
                memoryRecorder = new MemoryRecorder(user, new CollectionController().getCollectedWords(user), source);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        if (memoryRecorder.getWordsToStudySize() != 0) {
            new MemoryPanel(user, memoryRecorder.next(), memoryRecorder);
            mainView.dispose();
        }
    }
}
