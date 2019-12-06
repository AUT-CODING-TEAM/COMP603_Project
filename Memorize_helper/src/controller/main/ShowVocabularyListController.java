/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import model.*;
import view.rankList.RankListPanel;
import view.vocabularyList.VocabularyListPanel;

/**
 *
 * @author ThinkPad
 */
public class ShowVocabularyListController  extends MainViewControllerTemplate{
    public ShowVocabularyListController(JFrame mainView, User user) {
        super(mainView, user);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("单词列表JFrame");
        new VocabularyListPanel(mainView, user);
        mainView.setVisible(false);
    }
}
