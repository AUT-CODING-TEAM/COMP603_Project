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
    private JList list_sP_searchResultList;
    private JFrame searchResultListFrame;
    private MemoryPanel memoryPanel;
    private WordExplainPage wordExplainPage;
    private MemoryRecorder memoryRecorder;

    //from SearchResultListPanel
    public WordDetailController(User user, JList list_sP_searchResultList, JFrame searchResultListFrame) {
        this.user = user;
        this.list_sP_searchResultList = list_sP_searchResultList;
        this.searchResultListFrame = searchResultListFrame;
    }

    // from MemoryPanel
    public WordDetailController(User user, WordExplainPage wordExplainPage, MemoryPanel memoryPanel, MemoryRecorder memoryRecorder) {
        this.user = user;
        this.wordExplainPage = wordExplainPage;
        this.memoryPanel = memoryPanel;
        this.memoryRecorder = memoryRecorder;
    }

    //from SearchResultListPanel
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (list_sP_searchResultList != null) {
            if (list_sP_searchResultList.getValueIsAdjusting()) {
                wordExplainPage = new WordExplainPage(list_sP_searchResultList.getSelectedValue().toString().trim());

                new WordExplainPanel(wordExplainPage, searchResultListFrame);
                searchResultListFrame.setVisible(false);
            }
        }

    }

    // from MemoryPanel
    @Override
    public void actionPerformed(ActionEvent e) {
        if (memoryPanel != null) {
            memoryRecorder.getWordsToReview().add(memoryPanel.getMemoryPage().getWordObj());
            new WordExplainPanel(wordExplainPage, memoryPanel.getMemoryFrame(), memoryRecorder, user);
            memoryPanel.getMemoryFrame().dispose();
        }
    }
}
