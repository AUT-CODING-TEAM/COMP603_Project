/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import model.*;
import view.WordExplainPanel;
import view.memory.MemoryPanel;

/**
 *
 * @author ThinkPad
 */
public class WordDetailController implements ListSelectionListener, ActionListener {

    private User user;
    private JList searchResultList;
    private JFrame searchResultListFrame;
    private MemoryPanel memoryPanel;
    private WordExplainPage wordExplainPage;
    private MemoryRecorder memoryRecorder;

    //from SearchResultListPanel
    public WordDetailController(User user, JList list_sP_searchResultList, JFrame searchResultListFrame) {
        this.user = user;
        this.searchResultList = list_sP_searchResultList;
        this.searchResultListFrame = searchResultListFrame;
    }

    // from MemoryPanel
    public WordDetailController(User user, WordExplainPage wordExplainPage, MemoryPanel memoryPanel) {
        this.user = user;
        this.wordExplainPage = wordExplainPage;
        this.memoryPanel = memoryPanel;
        this.memoryRecorder = memoryPanel.getMemoryRecorder();
    }

    //from SearchResultListPanel
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (searchResultList != null) {
            if (searchResultList.getValueIsAdjusting()) {
                try {
                    wordExplainPage = new WordExplainPage(searchResultList.getSelectedValue().toString().trim());
                    new WordExplainPanel(wordExplainPage, searchResultListFrame);
                    searchResultListFrame.setVisible(false);
                } catch (Exception ex) {

                }

            }
        }

    }

    // from MemoryPanel
    @Override
    public void actionPerformed(ActionEvent e) {
        if (memoryPanel != null) {
            memoryRecorder.getWordsToStudy().add(memoryPanel.getMemoryPage().getWordObj());
            new WordExplainPanel(wordExplainPage, memoryPanel, user);
            memoryPanel.getMemoryFrame().dispose();
        }
    }
}
