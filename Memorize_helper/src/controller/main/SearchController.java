/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.main;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.Document;
import model.SearchResultInfo;
import model.User;
import view.main.MainView;
import view.search.*;

/**
 *
 * @author ThinkPad
 */
public class SearchController implements DocumentListener {

    private SearchResultListPanel searchResultListPanel;
    private User user;
    private String inputKeyWord;

    public SearchController(SearchResultListPanel searchResultListPanel) {
        this.searchResultListPanel = searchResultListPanel;
        this.user = user;
    }

    private void response() {
        try {
//            Document doc = tf_sRLP_keyword.getDocument();
            Document doc = searchResultListPanel.getTf_sRLP_keyword().getDocument();
            this.inputKeyWord = doc.getText(0, doc.getLength());
        } catch (Exception e) {

        }
        
        if ("".equals(inputKeyWord)) {
            searchResultListPanel.backToMain();
            searchResultListPanel.getSearchResultListFrame().dispose();
        } else {
            searchResultListPanel.getList_sP_searchResultList().setListData(new SearchResultInfo(inputKeyWord).getSearchResultListInfo());
        }

    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        response();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        response();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
    }
}
